package com.github.webicitybrowser.thready.windowing.skija.imp;

import org.lwjgl.glfw.GLFW;

import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.thready.windowing.core.event.keyboard.CharScreenEvent;
import com.github.webicitybrowser.thready.windowing.core.event.keyboard.KeyScreenEvent;
import com.github.webicitybrowser.thready.windowing.core.event.keyboard.KeyboardConstants;

public final class SkijaKeyboardEventListeners {

	private SkijaKeyboardEventListeners() {}

	public static void setupEventListeners(long windowId, ScreenContent screenContent) {
		GLFW.glfwSetCharCallback(windowId, ($, cp) -> {
			screenContent.handleEvent(new CharScreenEvent() {
				@Override
				public int getCodepoint() {
					return cp;
				}
			}, null);
		});
		
		GLFW.glfwSetKeyCallback(windowId, ($, keycodes, scancode, action, mods) -> {
			screenContent.handleEvent(new KeyScreenEvent() {
				@Override
				public int getCode() {
					return keycodes;
				}
				
				@Override
				public int getAction() {
					return remapKeyAction(action);
				}

				@Override
				public String getName() {
					return GLFW.glfwGetKeyName(action, scancode);
				}
			}, null);
		});
	}
	
	private static int remapKeyAction(int action) {
		if (action == GLFW.GLFW_RELEASE) {
			return KeyboardConstants.KEY_RELEASE;
		} else if (action == GLFW.GLFW_PRESS) {
			return KeyboardConstants.KEY_PRESS;
		} else if (action == GLFW.GLFW_REPEAT) {
			return KeyboardConstants.KEY_HOLD;
		}
		return -1;
	}
	
	public static int remapKeyCode(int kc) {
		return kc; // TODO: These should technically be re-mapped
	}
	
}
