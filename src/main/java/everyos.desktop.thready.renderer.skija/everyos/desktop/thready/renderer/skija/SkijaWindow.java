package everyos.desktop.thready.renderer.skija;

import org.lwjgl.glfw.GLFW;

import everyos.desktop.thready.core.graphics.Screen;

public class SkijaWindow {
	
	private final long windowId;
	private final Screen screen;

	public SkijaWindow(long glfwWindowId) {
		this.windowId = glfwWindowId;
		this.screen = new GLFWScreen(glfwWindowId);
	}

	public Screen getScreen() {
		return this.screen;
	}
	
	public void setVisible(boolean visible) {
		GLFW.glfwSetWindowAttrib(windowId, GLFW.GLFW_VISIBLE, glfwBool(visible));
	}
	
	public void setDecorated(boolean decorated) {
		GLFW.glfwSetWindowAttrib(windowId, GLFW.GLFW_DECORATED, glfwBool(decorated));
	}
	
	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(windowId, title);
	}

	private int glfwBool(boolean bool) {
		return bool ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE;
	}
	
	public static SkijaWindow createWindow() {
		//TODO: Ensure that we are on a dedicated thread
		initGlfw();
		long windowId = GLFW.glfwCreateWindow(800, 600, "Untitled Application", GLFW.GLFW_DONT_CARE, GLFW.GLFW_DONT_CARE);
		
		return new SkijaWindow(windowId);
	}

	private static void initGlfw() {
		if (GLFW.glfwInit() == false) {
			throw new Error("GLFW init failed");
		}
	}
	
}
