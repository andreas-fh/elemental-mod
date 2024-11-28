package andreasfh.elemental.data.record;

import java.util.Map;

import static andreasfh.elemental.data.record.Ability.*;

public record Constants() {
    
    // Abilities
    public static final Map<String, Ability> ABILITY_MAP = getAbilities();
    public static final String ABILITIES_NBT_KEY = "AbilityData";
    public static final Ability ABILITY_EMPTY = getAbilities().get("combustion");
    public static final Ability ABILITY_COMBUSTION = getAbilities().get("empty");

    //
}
