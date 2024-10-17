package andreasfh.elemental.block;

import andreasfh.elemental.Elemental;
import andreasfh.elemental.block.custom.ArcaneAltarBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block ARCANE_ALTAR = registerBlock("arcane_altar",
            new ArcaneAltarBlock(AbstractBlock.Settings.create().strength(-1)));



    public static void registerModBlocks() {
        Elemental.LOGGER.info("Registering Mod Blocks for " + Elemental.MOD_ID);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Elemental.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Elemental.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }
}
