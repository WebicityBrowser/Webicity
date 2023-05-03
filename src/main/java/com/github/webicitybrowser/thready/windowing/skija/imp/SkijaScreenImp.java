package com.github.webicitybrowser.thready.windowing.skija.imp;

import org.lwjgl.glfw.GLFW;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent.ScreenContentRedrawContext;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.SkijaRootCanvas2D;
import com.github.webicitybrowser.thready.windowing.skija.SkijaScreen;
import com.github.webicitybrowser.thready.windowing.skija.SkijaWindow;

import io.github.humbleui.skija.DirectContext;

public class SkijaScreenImp implements SkijaScreen {

	private static final int FULL_TICKS = 2;
	
	private final SkijaWindow window;
	private final Long windowId;
	private final DirectContext directContext = DirectContext.makeGL();
	private final SkijaGraphicsSystem graphicsSystem;
	
	private int ticksLeft = FULL_TICKS;
	private ScreenContent screenContent;
	private SkijaRootCanvas2D canvas;
	private AbsoluteSize oldScreenSize;
	
	public SkijaScreenImp(SkijaWindow window, long windowId, SkijaGraphicsSystem graphicsSystem) {
		this.window = window;
		this.windowId = windowId;
		this.graphicsSystem = graphicsSystem;
	}

	@Override
	public void setScreenContent(ScreenContent content) {
		this.screenContent = content;
		ticksLeft = FULL_TICKS;
		// TODO: Release event listeners
		SkijaEventListeners.setupEventListeners(windowId, content);
	}

	@Override
	public void tick() {
		if (!shouldPerformRedraw()) {
			return;
		}
		
		GLFW.glfwMakeContextCurrent(windowId);
		regenerateCanvasIfNeeded();
		resetTickChecks();
		screenContent.redraw(new ScreenContentRedrawContext(
			canvas, window.getSize(),
			graphicsSystem.getResourceLoader()));
		canvas.flush();
		GLFW.glfwSwapBuffers(windowId);
	}

	private boolean shouldPerformRedraw() {
		if (screenContent == null) {
			return false;
		}
		if (redrawConditionsTriggered() || screenContent.redrawRequested()) {
			ticksLeft = FULL_TICKS;
		}
		return ticksLeft > 0;
	}

	private boolean redrawConditionsTriggered() {
		return pollScreenSizeChanged();
	}
	
	private boolean pollScreenSizeChanged() {
		AbsoluteSize screenSize = window.getSize();
		boolean changed = !window.getSize().equals(oldScreenSize);
		if (changed) {
			oldScreenSize = screenSize;
			regenerateCanvas();
		}
		
		return changed;
	}

	private void regenerateCanvasIfNeeded() {
		if (canvas == null) {
			regenerateCanvas();
		}
	}
	
	private void regenerateCanvas() {
		this.canvas = SkijaRootCanvas2DImp.create(directContext, window.getSize());
	}

	private void resetTickChecks() {
		ticksLeft--;
	}

}
