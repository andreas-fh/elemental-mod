package andreasfh.elemental.item;

import andreasfh.elemental.Elemental;
import andreasfh.elemental.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup ELEMENTAL_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Elemental.MOD_ID, "elemental_item_group"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.KINDLING))
                    .displayName(Text.translatable("itemgroup.elemental.elemental"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.KINDLING);
                        entries.add(ModItems.SCROLL_COMBUSTION);

                        entries.add(ModBlocks.ARCANE_ALTAR);
                    }).build());


    public static void registerModItemGroups() {
        Elemental.LOGGER.info("Registering item groups for " + Elemental.MOD_ID);
    }
}
