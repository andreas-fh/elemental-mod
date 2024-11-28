package andreasfh.elemental.util;

import static andreasfh.elemental.data.record.Constants.*;

import andreasfh.elemental.data.record.Ability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class AbilityNBTStorage {

    public static boolean isAbilityUnlocked(PlayerEntity player, int abilityIndex) {
        NbtCompound abilitiesTag = getAbilitiesTag(player);
        return abilitiesTag.getBoolean("ability_" + Ability.getAbilityKeyFromIndex(abilityIndex));
    }

    public static void unlockAbility(PlayerEntity player, int abilityIndex) {
        NbtCompound abilitiesTag = getAbilitiesTag(player);
        abilitiesTag.putBoolean("ability_" + Ability.getAbilityKeyFromIndex(abilityIndex), true);
        saveAbilitiesTag(player, abilitiesTag);
    }

    public static NbtCompound getAbilitiesTag(PlayerEntity player) {
        NbtCompound playerData = new NbtCompound();
        player.writeCustomDataToNbt(playerData);
        return playerData.getCompound(ABILITIES_NBT_KEY);
    }

    public static void saveAbilitiesTag(PlayerEntity player, NbtCompound abilitiesTag) {
        NbtCompound playerData = new NbtCompound();
        player.writeCustomDataToNbt(playerData);
        playerData.put(ABILITIES_NBT_KEY, abilitiesTag);
        player.readCustomDataFromNbt(playerData);
    }
}
