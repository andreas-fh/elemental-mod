package andreasfh.elemental.client.hud;

import andreasfh.elemental.Elemental;
import andreasfh.elemental.util.Ability;
import andreasfh.elemental.util.BooleanUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class AbilityHudOverlay implements HudRenderCallback {
    private static final Identifier ABILITY_HUD_TEXTURE = Identifier.of(Elemental.MOD_ID, "textures/gui/hud/abilityhud.png");
    private Ability FIRST_ABILITY = Ability.getAbility("empty");
    private Ability SECOND_ABILITY = Ability.getAbility("empty");
    private Ability THIRD_ABILITY = Ability.getAbility("empty");

    public static void registerAbilityHudOverlay() {
        Elemental.LOGGER.info("Registering HUD elements for " + Elemental.MOD_ID);
        HudRenderCallback.EVENT.register(new AbilityHudOverlay());
    }

    public void selectAbilities(Ability firstAbility, Ability secondAbility, Ability thirdAbility) {
        FIRST_ABILITY = firstAbility;
        SECOND_ABILITY = secondAbility;
        THIRD_ABILITY = thirdAbility;
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        BooleanUtil boolInstance = new BooleanUtil();
        if (MinecraftClient.getInstance().options.hudHidden || boolInstance.getBool(BooleanUtil.debugScreen)) return;

        int x = 0;
        int y = 10;
        drawContext.drawTexture(ABILITY_HUD_TEXTURE, x, y, 0, 0, 32, 94, 32, 94);
        drawContext.drawTexture(FIRST_ABILITY.getIdentifier(), x+6, y+6, 0, 0, 20, 20, 20, 20);
        drawContext.drawTexture(SECOND_ABILITY.getIdentifier(), x+6, y+37, 0, 0, 20, 20, 20, 20);
        drawContext.drawTexture(THIRD_ABILITY.getIdentifier(), x+6, y+68, 0, 0, 20, 20, 20, 20);
    }
}
