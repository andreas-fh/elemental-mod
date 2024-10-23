package andreasfh.elemental.component;

import andreasfh.elemental.Elemental;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModComponents {
    public static final ComponentType<String> SCROLL_COLOR =
            register("color", builder -> builder.codec(Codec.STRING));



    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Elemental.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponentTypes() {
        Elemental.LOGGER.info("Registering Data Components for " + Elemental.MOD_ID);
    }

}
