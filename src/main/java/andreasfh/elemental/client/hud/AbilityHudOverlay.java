package andreasfh.elemental.client.hud;

import andreasfh.elemental.Elemental;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class AbilityHudOverlay implements HudRenderCallback {
    private static final Identifier ABILITY_COMBUSTION = Identifier.of(Elemental.MOD_ID,
            "textures/abilities/combustion.png");

    public static void registerAbilityHudOverlay() {
        Elemental.LOGGER.info("Registering HUD elements for " + Elemental.MOD_ID);
        HudRenderCallback.EVENT.register(new AbilityHudOverlay());
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = drawContext.getScaledWindowWidth();
            int height = drawContext.getScaledWindowHeight();

            x = width / 2;
            y = height;
        }
        drawContext.drawTexture(ABILITY_COMBUSTION, x, y, 0, 0, 100, 100);
    }
}
