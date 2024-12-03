package andreasfh.elemental.data.record;

import static andreasfh.elemental.data.record.Constants.*;
import andreasfh.elemental.Elemental;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public record Ability(String name, String description, float cooldown, Identifier identifier) {
    private static final Map<String, Ability> abilities = new HashMap<>();

    public static void registerAbilities() {
        String modid = Elemental.MOD_ID;
        String texturePath = "textures/gui/hud/abilities/";

        abilities.put("empty", new Ability("", "", 0,
                Identifier.of(modid, texturePath + "empty.png")));
        abilities.put("combustion", new Ability("Touch of Combustion",
                "Make any block become a live explosion with the touch of a hand",
                0, Identifier.of(modid, texturePath + "combustion.png")));
    }

    public static Map<String, Ability> getAbilities() {
        return abilities;
    }
}