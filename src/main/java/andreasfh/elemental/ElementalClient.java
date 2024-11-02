package andreasfh.elemental;

import andreasfh.elemental.client.block.ArcaneAltarScrollRenderer;
import andreasfh.elemental.client.hud.AbilityHudOverlay;
import andreasfh.elemental.util.Ability;
import andreasfh.elemental.util.BooleanUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ElementalClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AbilityHudOverlay.registerAbilityHudOverlay();
        ArcaneAltarScrollRenderer.registerRendering();
        BooleanUtil.registerListener();
    }
}
