package me.sootysplash.bind.mixin;

import me.sootysplash.bind.TemplateMod;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InputUtil.class)
public class InputUtilMixin {
	@Inject(at = @At(value = "HEAD"), method = "setKeyboardCallbacks", cancellable = true)
	private static void forwardBind(long handle, GLFWKeyCallbackI keyCallback, GLFWCharModsCallbackI charModsCallback, CallbackInfo ci) {
		GLFW.glfwSetKeyCallback(handle, (window, key, scancode, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                TemplateMod.lastPress.put(key, System.currentTimeMillis());
            }

            keyCallback.invoke(window, key, scancode, action, mods);
        });
		GLFW.glfwSetCharModsCallback(handle, charModsCallback);
		ci.cancel();
	}
}