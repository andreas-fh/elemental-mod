package andreasfh.elemental.component.custom;

import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class AbilityComponentImpl implements AbilityComponent {
    private final Map<String, Boolean> abilitiesUnlockMap = new HashMap<>();

    @Override
    public Map<String, Boolean> getAbilitiesUnlock() {
        return abilitiesUnlockMap;
    }

    @Override
    public void unlockAbility(String ability) {
        abilitiesUnlockMap.put(ability, true);
    }

    @Override
    public void lockAbility(String ability) {
        abilitiesUnlockMap.put(ability, false);
    }

    @Override
    public boolean isAbilityUnlocked(String ability) {
        return abilitiesUnlockMap.getOrDefault(ability, false);
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        // Serialize abilities into nbt
        abilitiesUnlockMap.forEach(nbt::putBoolean);
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        abilitiesUnlockMap.clear();
        // Deserialize abilities from nbt
        for (String key : nbt.getKeys()) {
            abilitiesUnlockMap.put(key, nbt.getBoolean(key));
        }
    }
}
