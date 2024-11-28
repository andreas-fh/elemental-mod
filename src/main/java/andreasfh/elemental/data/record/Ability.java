package andreasfh.elemental.data.record;

import static andreasfh.elemental.data.record.Constants.*;
import andreasfh.elemental.Elemental;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static String getAbilityKeyFromIndex(int abilityIndex) {
        List<String> keys = new ArrayList<>(ABILITY_MAP.keySet());

        if (abilityIndex >= 0 && abilityIndex < keys.size()) {
            return keys.get(abilityIndex);
        } else {
            throw new IndexOutOfBoundsException("Invalid ability index: " + abilityIndex);
        }
    }

    public static int getAbilityIndex(Ability ability) {
        int index = 0;
        for (Ability key : ABILITY_MAP.values()) {
            if (key.equals(ability)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}