package andreasfh.elemental.blockentity.custom;

import andreasfh.elemental.blockentity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ArcaneAltarBlockEntity extends BlockEntity {
    private ItemStack displayedItem = ItemStack.EMPTY;

    public ArcaneAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARCANE_ALTAR_ENTITY, pos, state);
    }

    public void setDisplayedItem(ItemStack itemStack) {
        this.displayedItem = itemStack;
    }

    public ItemStack getDisplayedItem() {
        return this.displayedItem;
    }
}