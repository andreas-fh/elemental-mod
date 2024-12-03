package andreasfh.elemental.mixin;

import andreasfh.elemental.component.custom.AbilityComponentImpl;
import andreasfh.elemental.component.custom.AbilityComponentProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements AbilityComponentProvider {

    @Unique
    private final AbilityComponentImpl abilityComponent = new AbilityComponentImpl();

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("AbilityComponent")) {
            abilityComponent.readFromNbt(nbt.getCompound("AbilityComponent"));
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound componentData = new NbtCompound();
        abilityComponent.writeToNbt(componentData);
        nbt.put("AbilityComponent", componentData);
    }

    @Override
    public AbilityComponentImpl getAbilityComponent() {
        return this.abilityComponent;
    }
}
