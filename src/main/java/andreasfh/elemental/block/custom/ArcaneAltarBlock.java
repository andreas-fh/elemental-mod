package andreasfh.elemental.block.custom;

import andreasfh.elemental.item.ModItems;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArcaneAltarBlock extends Block {

    private int particleBatchCounter = 0;
    private int ticksSinceLastBatch = 0;
    private boolean shouldSpawnParticles = false;
    private static boolean isTickEventRegistered = false;
    private BlockPos particleSpawnPos;

    public ArcaneAltarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ItemStack stack = player.getMainHandStack();

        if (stack.isOf(ModItems.KINDLING)) {
            if (world.isClient) {
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
        if (!shouldSpawnParticles) {
            return;
        }

        // Update ticks and check if it's time to spawn the next batch
        ticksSinceLastBatch++;

        int delayBetweenBatches = 1;

        if (ticksSinceLastBatch >= delayBetweenBatches) {

            if (client.world != null && particleSpawnPos != null) {

                spawnParticleBatch(client.world, particleSpawnPos, particleBatchCounter);
                particleBatchCounter++;
                ticksSinceLastBatch = 0;  // Reset tick counter

                // Stop spawning if we have completed all batches
                if (particleBatchCounter >= 50) {
                    shouldSpawnParticles = false;  // Disable further particle spawning
                }
            }
        }
    }


    private void spawnParticleBatch(World world, BlockPos pos, int batchNumber) {
        double radius = 0.5f;  // Radius of the spiral
        int particlesPerRevolution = 25;  // Number of particles per revolution
        int particlesInBatch = 1;  // Particles per batch
        double heightStep = 0.02;  // Height increase per particle

        double centerX = pos.getX() + 0.5f;
        double centerZ = pos.getZ() + 0.5f;

        for (int i = 0; i < particlesInBatch; i++) {
            int particleIndex = batchNumber * particlesInBatch + i;  // Calculate the overall particle index
            double angle = 2 * Math.PI * (particleIndex % particlesPerRevolution) / particlesPerRevolution;
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            double currentY = pos.getY() + 1.1 + (particleIndex * heightStep);

            // Spawn particles (END_ROD in this case) at the block position
            world.addParticle(ParticleTypes.END_ROD,
                    centerX + offsetX,
                    currentY,
                    centerZ + offsetZ,
                    0.0, 0.0, 0.0);  // No velocity for static particles
        }
    }
}