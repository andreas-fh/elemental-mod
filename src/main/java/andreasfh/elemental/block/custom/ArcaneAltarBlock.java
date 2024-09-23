package andreasfh.elemental.block.custom;

import andreasfh.elemental.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArcaneAltarBlock extends Block {
    public ArcaneAltarBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isOf(ModItems.KINDLING)) {
            if (!world.isClient) {
                spawnParticles(world, pos);
            }
        }


        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    private void spawnParticles(World world, BlockPos pos) {
        double radius = 0.5f; // Radius of the spiral
        int particlesPerRevolution = 20; // Number of particles per revolution
        int totalParticles = 100;
        double heightStep = 0.1; // Height increase per particle

        double centerX = pos.getX() + 0.5f;
        double centerZ = pos.getZ() + 0.5f;

        for (int i = 0; i < totalParticles; i++) {
            // Angle in radians
            double angle = 2 * Math.PI * (i % particlesPerRevolution) / particlesPerRevolution;
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            double currentY = pos.getY() + 1 + (i * heightStep);

            world.addParticle(ParticleTypes.END_ROD,
                    centerX + offsetX,
                    currentY,
                    centerZ + offsetZ,
                    0.0, 0.0, 0.0);
        }
    }
}
