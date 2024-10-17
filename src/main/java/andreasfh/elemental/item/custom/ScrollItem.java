package andreasfh.elemental.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class ScrollItem extends Item {

    public ScrollItem(Settings settings, String color) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if (clickedBlock == Blocks.COAL_BLOCK) {
            if (!world.isClient()) {
                world.removeBlock(context.getBlockPos(), false);
            }
        }


        return super.useOnBlock(context);
    }
}
