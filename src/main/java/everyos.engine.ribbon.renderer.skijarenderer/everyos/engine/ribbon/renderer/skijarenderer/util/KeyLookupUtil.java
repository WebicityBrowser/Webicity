package everyos.engine.ribbon.renderer.skijarenderer.util;

import org.lwjgl.glfw.GLFW;

import everyos.engine.ribbon.core.event.Key;

public final class KeyLookupUtil {
	private KeyLookupUtil() {}
	
	public static Key query(int kc) {
		switch(kc) {
			case GLFW.GLFW_KEY_ENTER:
				return Key.ENTER;
			case GLFW.GLFW_KEY_BACKSPACE:
				return Key.BACKSPACE;
			default:
				return Key.UNKNOWN;
		}
	}

}
