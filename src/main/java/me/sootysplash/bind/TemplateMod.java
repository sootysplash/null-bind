package me.sootysplash.bind;

import net.minecraft.client.option.GameOptions;

import java.util.HashMap;

public class TemplateMod {
	public static final HashMap<Integer, Long> lastPress = new HashMap<>();

	public static Float getSideways(GameOptions settings) {
		if (settings.leftKey.isPressed() && settings.rightKey.isPressed()) {
			return lastPress.getOrDefault(settings.leftKey.boundKey.getCode(), 0L) >= lastPress.getOrDefault(settings.rightKey.boundKey.getCode(), 0L) ? 1f : -1f;
		} else {
			return null;
		}
	}
	public static Float getForward(GameOptions settings) {
		if (settings.forwardKey.isPressed() && settings.backKey.isPressed()) {
			return lastPress.getOrDefault(settings.forwardKey.boundKey.getCode(), 0L) >= lastPress.getOrDefault(settings.backKey.boundKey.getCode(), 0L) ? 1f : -1f;
		} else {
			return null;
		}
	}
}