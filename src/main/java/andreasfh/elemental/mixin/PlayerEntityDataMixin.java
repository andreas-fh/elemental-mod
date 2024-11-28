package andreasfh.elemental.mixin;

import static andreasfh.elemental.data.record.Constants.*;
import andreasfh.elemental.util.AbilityNBTStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityDataMixin {

    @Unique
    private static boolean isWriting = false;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeAbilitiesToNbt(NbtCompound nbt, CallbackInfo info) {
        if (!isWriting) {
            isWriting = true;
            try {
                PlayerEntity player = (PlayerEntity) (Object) this;
                NbtCompound abilitiesData = AbilityNBTStorage.getAbilitiesTag(player);
                nbt.put(ABILITIES_NBT_KEY, abilitiesData);
            } finally {
                isWriting = false;
            }
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readAbilitiesFromNbt(NbtCompound nbt, CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (nbt.contains(ABILITIES_NBT_KEY)) {
            AbilityNBTStorage.saveAbilitiesTag(player, nbt.getCompound(ABILITIES_NBT_KEY));
        }
    }
}
