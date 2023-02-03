package everyos.desktop.thready.renderer.skija;

import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import everyos.desktop.thready.core.gui.Screen;

public class SkijaWindow {
	
	private final long windowId;
	private final Screen screen;

	private SkijaWindow(long glfwWindowId) {
		this.windowId = glfwWindowId;
		this.screen = new SkijaScreen(glfwWindowId);
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
	
	public static void createWindow(Consumer<SkijaWindow> threadFunc) {
		SkijaThread.addScreen(() -> {
			initGlfw();
			long windowId = GLFW.glfwCreateWindow(800, 600, "Untitled Application", 0, 0);
			GLFW.glfwMakeContextCurrent(windowId);
			GL.createCapabilities();
			GLFW.glfwSetWindowPos(windowId, 100, 100);
			GLFW.glfwSwapInterval(1);
			
			SkijaWindow window = new SkijaWindow(windowId);
			threadFunc.accept(window);
			
			return (SkijaScreen) window.getScreen();
		});
	}

	private static void initGlfw() {
		if (GLFW.glfwInit() == false) {
			throw new Error("GLFW init failed");
		}
	}
	
}
