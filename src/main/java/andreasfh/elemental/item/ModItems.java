package andreasfh.elemental.item;

import andreasfh.elemental.Elemental;
import andreasfh.elemental.item.custom.ScrollItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item KINDLING = registerItem("kindling", new Item(new Item.Settings()));

    public static final Item SCROLL_COMBUSTION = registerItem("scroll_combustion", new ScrollItem(new Item.Settings(), "orange"));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Elemental.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Elemental.LOGGER.info("Registering mod items for " + Elemental.MOD_ID);
    }
}
