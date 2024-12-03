package andreasfh.elemental.data.record;

import java.util.Map;

import static andreasfh.elemental.data.record.Ability.*;

public record Constants() {
    
    // Abilities
    public static final Map<String, Ability> ABILITY_MAP = getAbilities();
    public static final Ability ABILITY_EMPTY = getAbilities().get("empty");

}
