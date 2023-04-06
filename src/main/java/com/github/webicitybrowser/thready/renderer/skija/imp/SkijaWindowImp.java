package com.github.webicitybrowser.thready.renderer.skija.imp;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import com.github.webicitybrowser.thready.windowing.skija.SkijaScreen;
import com.github.webicitybrowser.thready.windowing.skija.SkijaWindow;

public class SkijaWindowImp implements SkijaWindow {

	private final long windowId;
	
	private boolean windowClosed = false;

	public SkijaWindowImp(long windowId) {
		this.windowId = windowId;
	}
	
	@Override
	public SkijaScreen getScreen() {
		return new SkijaScreenImp();
	}
	
	@Override
	public void close() {
		this.windowClosed = true;
		GLFW.glfwDestroyWindow(windowId);
	}
	
	@Override
	public void setVisible(boolean visible) {
		GLFW.glfwSetWindowAttrib(windowId, GLFW.GLFW_VISIBLE, glfwBool(visible));
	}

	//
	
	@Override
	public boolean closed() {
		return windowClosed;
	}

	@Override
	public void tick() {
		closeWindowIfShouldClose();
	}
	
	//
	
	private void closeWindowIfShouldClose() {
		if (GLFW.glfwWindowShouldClose(windowId)) {
			close();
		}
	}

	private int glfwBool(boolean bool) {
		return bool ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE;
	}
	
	//

	public static SkijaWindow create() {
		initGlfw();
		long windowId = GLFW.glfwCreateWindow(800, 600, "Untitled Application", 0, 0);
		GLFW.glfwMakeContextCurrent(windowId);
		GL.createCapabilities();
		GLFW.glfwSetWindowPos(windowId, 200, 100);
		GLFW.glfwSwapInterval(1);
		
		SkijaWindow window = new SkijaWindowImp(windowId);
		
		return window;
	}
	
	private static void initGlfw() {
		if (GLFW.glfwInit() == false) {
			throw new Error("GLFW init failed");
		}
	}

}
