package andreasfh.elemental.util;

import andreasfh.elemental.Elemental;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class Ability {


    private final String name;
    private final String description;
    private final float cooldown;
    private final Identifier identifier;

    private static final Map<String, Ability> abilities = new HashMap<>();

    public static void registerAbilities() {
        String modid = Elemental.MOD_ID;
        String texturePath = "textures/gui/hud/abilities/";
        Elemental.LOGGER.info("Registering abilities for " + Elemental.MOD_ID);
        abilities.put("empty", new Ability("", "", 0,
                Identifier.of(modid, texturePath + "empty.png")));
        abilities.put("combustion", new Ability("Touch of Combustion",
                "Make any block become a live explosion with the touch of a hand",
                0, Identifier.of(modid, texturePath + "combustion.png")));
    }


    public Ability(String name, String description, float cooldown, Identifier identifier) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.identifier = identifier;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public float getCooldown() { return cooldown; }
    public Identifier getIdentifier() { return identifier; }

    public static void debug() {
        System.out.println(abilities.get("combustion").getDescription());
    }

    public static Ability getAbility(String key) {
        return abilities.get(key);
    }
}
