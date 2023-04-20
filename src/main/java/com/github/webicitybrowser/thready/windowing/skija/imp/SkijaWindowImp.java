package com.github.webicitybrowser.thready.windowing.skija.imp;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.SkijaScreen;
import com.github.webicitybrowser.thready.windowing.skija.SkijaWindow;

public class SkijaWindowImp implements SkijaWindow {

	private final long windowId;
	private final SkijaScreen screen;
	
	private boolean windowClosed = false;

	public SkijaWindowImp(long windowId, SkijaGraphicsSystem graphicsSystem) {
		this.windowId = windowId;
		this.screen = new SkijaScreenImp(this, windowId, graphicsSystem);
	}
	
	@Override
	public SkijaScreen getScreen() {
		return this.screen;
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
	
	@Override
	public AbsoluteSize getSize() {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetWindowSize(windowId, width, height);
		
		return new AbsoluteSize((float) width[0], (float) height[0]);
	}

	//
	
	@Override
	public boolean closed() {
		return windowClosed;
	}

	@Override
	public void tick() {
		closeWindowIfShouldClose();
		screen.tick();
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

	public static SkijaWindow create(SkijaGraphicsSystem graphicsSystem) {
		initGlfw();
		long windowId = GLFW.glfwCreateWindow(800, 600, "Untitled Application", 0, 0);
		GLFW.glfwMakeContextCurrent(windowId);
		GL.createCapabilities();
		GLFW.glfwSetWindowPos(windowId, 200, 100);
		GLFW.glfwSwapInterval(1);
		
		SkijaWindow window = new SkijaWindowImp(windowId, graphicsSystem);
		
		return window;
	}
	
	private static void initGlfw() {
		if (GLFW.glfwInit() == false) {
			throw new Error("GLFW init failed");
		}
	}

}
