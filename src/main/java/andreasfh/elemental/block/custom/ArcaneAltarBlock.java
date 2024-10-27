package andreasfh.elemental.block.custom;

import andreasfh.elemental.blockentity.custom.ArcaneAltarBlockEntity;
import andreasfh.elemental.component.ModComponents;
import andreasfh.elemental.item.custom.ScrollItem;
import andreasfh.elemental.util.ColorUtil;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.stream.Stream;

public class ArcaneAltarBlock extends BlockWithEntity {

    private float red;
    private float green;
    private float blue;

    private int particleBatchCounter = 0;
    private int ticksSinceLastBatch = 0;
    private boolean shouldSpawnParticles = false;
    private static boolean isTickEventRegistered = false;
    private BlockPos particleSpawnPos;

    private boolean isExplosionActive = false;
    private int explosionTicksRemaining = 0;
    private final int explosionDuration = 40;

    public ArcaneAltarBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack stack = player.getMainHandStack();
        ArcaneAltarBlockEntity arcaneAltarEntity = (ArcaneAltarBlockEntity) world.getBlockEntity(pos);

        if (stack.getItem() instanceof ScrollItem) {
            if (world.isClient && !shouldSpawnParticles && !isExplosionActive) {

                // Get scroll color
                String hexString = stack.getOrDefault(ModComponents.SCROLL_COLOR, "FFFFFF");
                int[] rgb = ColorUtil.hexToRGB(hexString);

                // Convert hex-color to RGB float values
                this.red = rgb[0] / 255.0f;
                this.green = rgb[1] / 255.0f;
                this.blue = rgb[2] / 255.0f;

                if (arcaneAltarEntity.getDisplayedItem() == ItemStack.EMPTY) {
                    arcaneAltarEntity.setDisplayedItem(stack);
                }

                // Display and remove item from inventory
                if (!player.isCreative()) {
                    stack.decrement(1);
                }

                arcaneAltarEntity.markDirty();
                world.updateListeners(pos, state, state, Block.NOTIFY_ALL);

                // Reset the counters
                particleBatchCounter = 0;
                ticksSinceLastBatch = 0;
                shouldSpawnParticles = true;
                particleSpawnPos = pos;  // Store the block position for particles

                // Ensure the event is only registered once
                if (!isTickEventRegistered) {
                    isTickEventRegistered = true;

                    // Register the tick event on the client side
                    ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
                }

            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }


    private void onClientTick(MinecraftClient client) {
        if (shouldSpawnParticles) {
            ticksSinceLastBatch++;
            int delayBetweenBatches = 1;

            // Check if it's time to spawn the next batch
            if (ticksSinceLastBatch >= delayBetweenBatches) {
                if (client.world != null && particleSpawnPos != null) {
                    spawnSpiralParticleBatch(client.world, particleSpawnPos, particleBatchCounter, red, green, blue);
                    particleBatchCounter++;
                    ticksSinceLastBatch = 0;  // Reset tick counter

                    // Stop spawning if we have completed all batches
                    if (particleBatchCounter >= 50) {
                        shouldSpawnParticles = false;
                        isExplosionActive = true;
                        explosionTicksRemaining = explosionDuration;
                        spawnParticleExplosion(client.world, particleSpawnPos);
                    }
                }
            }
        }

            // Explosion logic
            if (isExplosionActive && explosionTicksRemaining > 0) {
                explosionTicksRemaining--;

                if (explosionTicksRemaining <= 0) {
                    // Removed displayed item
                    BlockEntity blockEntity = client.world.getBlockEntity(particleSpawnPos);
                    if (blockEntity instanceof ArcaneAltarBlockEntity arcaneAltarEntity) {
                        arcaneAltarEntity.setDisplayedItem(ItemStack.EMPTY);

                        // Update client rendering
                        arcaneAltarEntity.markDirty();
                        client.world.updateListeners(particleSpawnPos, client.world.getBlockState(
                                        particleSpawnPos), client.world.getBlockState(particleSpawnPos),
                                Block.NOTIFY_ALL);

                        client.world.addParticle(ParticleTypes.TOTEM_OF_UNDYING,
                                particleSpawnPos.getX() + 0.5,
                                particleSpawnPos.getY() + 1.5,
                                particleSpawnPos.getZ() + 0.5,
                                0.0, 0.0, 0.0);
                    }
                    isExplosionActive = false;
                    shouldSpawnParticles = false;
                    particleBatchCounter = 0;
                    ticksSinceLastBatch = 0;
                }
            }
        }


    private void spawnSpiralParticleBatch(World world, BlockPos pos, int batchNumber, float red, float green, float blue) {
        if (!world.isClient) return;

        double radius = 0.5f;  // Radius of the spiral
        int particlesPerRevolution = 25;  // Number of particles per revolution
        int particlesInBatch = 1;  // Particles per batch
        double heightStep = 0.02;  // Height increase per particle

        double centerX = pos.getX() + 0.5f;
        double centerZ = pos.getZ() + 0.5f;

        // Define colored dust particle
        DustParticleEffect dustParticle = new DustParticleEffect(new Vector3f(red, green, blue), 1.0f);

        for (int i = 0; i < particlesInBatch; i++) {
            int particleIndex = batchNumber * particlesInBatch + i;  // Calculate overall particle index
            double angle = 2 * Math.PI * (particleIndex % particlesPerRevolution) / particlesPerRevolution;
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            double currentY = pos.getY() + 0.9 + (particleIndex * heightStep);

            world.addParticle(dustParticle,
                    centerX + offsetX,
                    currentY,
                    centerZ + offsetZ,
                    0.0, 0.0, 0.0);  // No velocity for static particles
        }
    }

    private void spawnParticleExplosion(World world, BlockPos pos) {
        double centerX = pos.getX() + 0.5;
        double centerY = pos.getY() + 1.5;
        double centerZ = pos.getZ() + 0.5;
        int explosionParticles = 60;
        double speedMultiplier = 0.8;

        for (int i = 0; i < explosionParticles; i++) {
            // Generate random spherical coordinates for even distribution
            double theta = world.random.nextDouble() * 2 * Math.PI;
            double phi = Math.acos(2 * world.random.nextDouble() - 1);

            // Convert spherical coordinates (angles) to Cartesian coordinates (X, Y, Z)
            double velocityX = Math.sin(phi) * Math.cos(theta);
            double velocityY = Math.sin(phi) * Math.sin(theta);
            double velocityZ = Math.cos(phi);

            world.addParticle(ParticleTypes.ENCHANT,
                    centerX, centerY, centerZ,
                    velocityX * speedMultiplier,
                    velocityY * speedMultiplier,
                    velocityZ * speedMultiplier
            );
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    private static final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(1, 0, 1, 15, 2, 15),
            Block.createCuboidShape(4, 2, 4, 12, 9, 12),
            Block.createCuboidShape(2, 8, 0, 14, 11, 2),
            Block.createCuboidShape(2, 8, 14, 14, 11, 16),
            Block.createCuboidShape(14, 8, 2, 16, 11, 14),
            Block.createCuboidShape(0, 8, 2, 2, 11, 14),
            Block.createCuboidShape(2, 11, 14, 14, 12, 16),
            Block.createCuboidShape(0, 11, 2, 2, 12, 14),
            Block.createCuboidShape(14, 11, 2, 16, 12, 14),
            Block.createCuboidShape(2, 11, 0, 14, 12, 2),
            Block.createCuboidShape(0, 10, 0, 2, 15, 2),
            Block.createCuboidShape(0, 10, 14, 2, 15, 16),
            Block.createCuboidShape(0, 15, 14, 2, 16, 16),
            Block.createCuboidShape(14, 15, 14, 16, 16, 16),
            Block.createCuboidShape(14, 15, 0, 16, 16, 2),
            Block.createCuboidShape(0, 15, 0, 2, 16, 2),
            Block.createCuboidShape(14, 10, 14, 16, 15, 16),
            Block.createCuboidShape(14, 10, 0, 16, 15, 2),
            Block.createCuboidShape(0, 12, 2, 2, 14, 4),
            Block.createCuboidShape(0, 12, 12, 2, 14, 14),
            Block.createCuboidShape(14, 12, 12, 16, 14, 14),
            Block.createCuboidShape(14, 12, 2, 16, 14, 4),
            Block.createCuboidShape(12, 12, 0, 14, 14, 2),
            Block.createCuboidShape(2, 12, 0, 4, 14, 2),
            Block.createCuboidShape(2, 12, 14, 4, 14, 16),
            Block.createCuboidShape(12, 12, 14, 14, 14, 16),
            Block.createCuboidShape(2, 11, 2, 6, 12, 4),
            Block.createCuboidShape(10, 11, 2, 14, 12, 4),
            Block.createCuboidShape(10, 11, 12, 14, 12, 14),
            Block.createCuboidShape(2, 11, 12, 6, 12, 14),
            Block.createCuboidShape(12, 11, 4, 14, 12, 6),
            Block.createCuboidShape(12, 11, 10, 14, 12, 12),
            Block.createCuboidShape(2, 11, 10, 4, 12, 12),
            Block.createCuboidShape(2, 11, 4, 4, 12, 6),
            Block.createCuboidShape(2, 8, 2, 14, 11, 14)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ArcaneAltarBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}