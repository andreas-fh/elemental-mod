package andreasfh.elemental.util;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class BooleanUtil {

    public static final String debugScreen = "isDebugScreenActive";

    private static boolean isDebugScreenActive = false;
    private static boolean wasF3Pressed; // Was f3 pressed last tick?

    public static void registerListener() {
        ClientTickEvents.END_CLIENT_TICK.register(BooleanUtil::clientTick);
    }

    private static void clientTick(MinecraftClient client) {
        if (client.player != null) {
            boolean isF3Pressed = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_F3) == GLFW.GLFW_PRESS;

            if (wasF3Pressed && !isF3Pressed) {
                setBool(debugScreen, !isDebugScreenActive);
            }
            wasF3Pressed = isF3Pressed;
        }
    }

    private static void setBool(String identifier, Boolean bool) {
        if (Objects.equals(identifier, debugScreen)) {
            isDebugScreenActive = bool;
        }
    }

    public boolean getBool(String identifier) {
        if (Objects.equals(identifier, debugScreen)) {
            return this.isDebugScreenActive;
        }
        else return false;
    }
}
