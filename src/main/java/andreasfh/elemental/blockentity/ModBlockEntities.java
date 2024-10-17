package andreasfh.elemental.blockentity;

import andreasfh.elemental.Elemental;
import andreasfh.elemental.block.ModBlocks;
import andreasfh.elemental.blockentity.custom.ArcaneAltarBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<ArcaneAltarBlockEntity> ARCANE_ALTAR_ENTITY =
            registerBlockEntity("arcane_altar_entity", BlockEntityType.Builder.create(
            ArcaneAltarBlockEntity::new, ModBlocks.ARCANE_ALTAR));



    public static void registerModBlockEntities() {
        Elemental.LOGGER.info("Registering Mod BlockEntities for " + Elemental.MOD_ID);
    }

    private static <T extends BlockEntity> BlockEntityType<T>
    registerBlockEntity(String name, BlockEntityType.Builder<T> builder) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Elemental.MOD_ID, name),
                builder.build(null));
    }
}
