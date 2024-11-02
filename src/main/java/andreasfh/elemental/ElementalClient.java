package andreasfh.elemental;

import andreasfh.elemental.client.block.ArcaneAltarScrollRenderer;
import andreasfh.elemental.client.hud.AbilityHudOverlay;
import andreasfh.elemental.util.BooleanUtil;
import net.fabricmc.api.ClientModInitializer;

public class ElementalClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AbilityHudOverlay.registerAbilityHudOverlay();
        ArcaneAltarScrollRenderer.registerRendering();
        BooleanUtil.registerListener();
    }
}
