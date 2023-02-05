package everyos.desktop.thready.renderer.skija;

import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import everyos.desktop.thready.core.gui.Screen;
import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.imp.AbsolutePositionImp;

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
	
	//
	
	public void setVisible(boolean visible) {
		GLFW.glfwSetWindowAttrib(windowId, GLFW.GLFW_VISIBLE, glfwBool(visible));
	}
	
	public void setDecorated(boolean decorated) {
		GLFW.glfwSetWindowAttrib(windowId, GLFW.GLFW_DECORATED, glfwBool(decorated));
	}
	
	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(windowId, title);
	}
	
	//
	
	public void minimize() {
		GLFW.glfwIconifyWindow(windowId);
	}
	
	public void restore() {
		if (isMaximized()) {
			GLFW.glfwRestoreWindow(windowId);
		} else {
			GLFW.glfwMaximizeWindow(windowId);
		}
	}

	public void close() {
		GLFW.glfwSetWindowShouldClose(windowId, true);
	}
	
	//
	
	public void setPosition(AbsolutePosition position) {
		GLFW.glfwSetWindowPos(windowId, (int) position.getX(), (int) position.getY());
	}
	
	public AbsolutePosition getPosition() {
		int[] xpos = new int[1], ypos = new int[1];
		GLFW.glfwGetWindowPos(windowId, xpos, ypos);
		return new AbsolutePositionImp(xpos[0], ypos[0]);
	}
	
	//
	
	private boolean isMaximized() {
		return GLFW.glfwGetWindowAttrib(windowId, GLFW.GLFW_MAXIMIZED) == GLFW.GLFW_TRUE;
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
			GLFW.glfwSetWindowPos(windowId, 200, 100);
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
