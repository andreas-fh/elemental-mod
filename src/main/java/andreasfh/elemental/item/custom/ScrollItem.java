package andreasfh.elemental.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class ScrollItem extends Item {
    public ScrollItem(Settings settings) {
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
