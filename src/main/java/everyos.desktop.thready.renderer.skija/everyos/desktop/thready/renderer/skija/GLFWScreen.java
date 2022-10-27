package everyos.desktop.thready.renderer.skija;

import org.lwjgl.glfw.GLFW;

import everyos.desktop.thready.core.graphics.Screen;
import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;

public class GLFWScreen implements Screen {

	private final long windowId;
	
	private InvalidationLevel invalidationLevel;
	private Component rootComponent;
	private LookAndFeel lookAndFeel;
	
	public GLFWScreen(long glfwWindowId) {
		this.windowId = glfwWindowId;
	}

	@Override
	public void setGUI(Component component, LookAndFeel lookAndFeel) {
		this.invalidationLevel = InvalidationLevel.BOX;
		this.rootComponent = component;
		this.lookAndFeel = lookAndFeel;
	}
	
	
	
	private boolean canRenderGui() {
		return
			rootComponent != null &&
			lookAndFeel != null &&
			windowIsVisible();
	}

	private boolean windowIsVisible() {
		return glfwBool(GLFW.glfwGetWindowAttrib(windowId, GLFW.GLFW_VISIBLE));
	}

	private boolean glfwBool(int bool) {
		return bool == GLFW.GLFW_TRUE;
	}

}
