package com.github.webicitybrowser.thready.windowing.skija.imp;

import org.lwjgl.glfw.GLFW;

import com.github.webicitybrowser.thready.drawing.skija.imp.SkijaRootCanvas2DImp;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.thready.windowing.skija.SkijaRootCanvas2D;
import com.github.webicitybrowser.thready.windowing.skija.SkijaScreen;
import com.github.webicitybrowser.thready.windowing.skija.SkijaWindow;

import io.github.humbleui.skija.DirectContext;

public class SkijaScreenImp implements SkijaScreen {

	private static final int FULL_TICKS = 2;
	
	private final SkijaWindow window;
	private final Long windowId;
	private final DirectContext directContext = DirectContext.makeGL();
	
	private int ticksLeft = FULL_TICKS;
	private ScreenContent screenContent;
	private SkijaRootCanvas2D canvas;
	
	public SkijaScreenImp(SkijaWindow window, long windowId) {
		this.window = window;
		this.windowId = windowId;
	}

	@Override
	public void setScreenContent(ScreenContent content) {
		this.screenContent = content;
		ticksLeft = FULL_TICKS;
	}

	@Override
	public void tick() {
		if (!shouldPerformRedraw()) {
			return;
		}
		
		GLFW.glfwMakeContextCurrent(windowId);
		regenerateCanvasIfNeeded();
		resetTickChecks();
		screenContent.redraw(canvas, window.getSize());
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
		// TODO Auto-generated method stub
		return false;
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
