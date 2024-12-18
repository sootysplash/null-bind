package me.sootysplash.bind.mixin;

import me.sootysplash.bind.TemplateMod;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin {
	@Shadow @Final private GameOptions settings;

	@Unique
	private static float getMovementMultiplier(boolean positive, boolean negative) {
		if (positive == negative) {
			return 0.0F;
		} else {
			return positive ? 1.0F : -1.0F;
		}
	}

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyboardInput;getMovementMultiplier(ZZ)F", ordinal = 0), method = "tick")
	private float forwardHook(boolean positive, boolean negative) {
		Float forw = TemplateMod.getForward(this.settings);
		if (forw != null) {
			return forw;
		} else {
			return getMovementMultiplier(positive, negative);
		}
	}

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyboardInput;getMovementMultiplier(ZZ)F", ordinal = 1), method = "tick")
	private float sideHook(boolean positive, boolean negative) {
		Float side = TemplateMod.getSideways(this.settings);
		if (side != null) {
			return side;
		} else {
			return getMovementMultiplier(positive, negative);
		}
	}

}