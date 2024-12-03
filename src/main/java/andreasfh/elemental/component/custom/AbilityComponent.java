package andreasfh.elemental.component.custom;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;

import java.util.Map;

public interface AbilityComponent {

    // Declare API components
    Map<String, Boolean> getAbilitiesUnlock();
    void unlockAbility(String ability);
    void lockAbility(String ability);
    boolean isAbilityUnlocked(String ability);

    void writeToNbt(NbtCompound nbt);
    void readFromNbt(NbtCompound nbt);

    Codec<AbilityComponent> CODEC = Codec.unit(AbilityComponentImpl::new);
}
