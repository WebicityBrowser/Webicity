package com.github.webicitybrowser.thready.windowing.skija.imp;

import org.lwjgl.glfw.GLFW;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseConstants;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseScreenEvent;

public final class SkijaMouseEventListeners {
	
	private SkijaMouseEventListeners() {}
	
	public static void setupEventListeners(long windowId, ScreenContent screenContent) {
		setupMouseListener(windowId, screenContent);
	}

	private static void setupMouseListener(long windowId, ScreenContent screenContent) {
		setupClickCallback(windowId, screenContent);
		setupMouseMoveCallback(windowId, screenContent);
	}

	private static void setupClickCallback(long windowId, ScreenContent screenContent) {
		GLFW.glfwSetMouseButtonCallback(windowId, ($, button, action, mods) -> {
			int mappedButton = fixButton(button);
			int mappedAction = fixAction(action);
			
			double[] x = new double[1], y = new double[1];
			GLFW.glfwGetCursorPos(windowId, x, y);
			
			sendMouseMessage(
				windowId, screenContent,
				(float) x[0], (float) y[0],
				mappedButton, mappedAction);
		});
	}
	
	private static void setupMouseMoveCallback(long windowId, ScreenContent screenContent) {
		GLFW.glfwSetCursorPosCallback(windowId, ($, dx, dy) -> {
			float x = (float) dx, y = (float) dy;
			if (GLFW.glfwGetMouseButton(windowId, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {
				sendMouseMessage(windowId, screenContent, x, y, MouseConstants.LEFT_BUTTON, MouseConstants.DRAG);
			} else if (GLFW.glfwGetMouseButton(windowId, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS) {
				sendMouseMessage(windowId, screenContent, x, y, MouseConstants.RIGHT_BUTTON, MouseConstants.DRAG);
			} else {
				sendMouseMessage(windowId, screenContent, x, y, 0, MouseConstants.MOVE);
			}
		});
	}
	
	private static void sendMouseMessage(long windowId, ScreenContent screenContent, float x, float y, int button, int action) {
		int[] winx = new int[1], winy = new int[1];
		GLFW.glfwGetWindowPos(windowId, winx, winy);
		
		AbsolutePosition viewportPosition = new AbsolutePosition(x, y);
		AbsolutePosition screenPosition = new AbsolutePosition(winx[0] + x, winy[0] + y);
		
		
		screenContent.handleEvent(new MouseScreenEvent() {
				@Override
				public int getButton() {
					return button;
				}
				
				@Override
				public int getAction() {
					return action;
				}

				@Override
				public boolean isExternal() {
					return false;
				}

				@Override
				public AbsolutePosition getViewportPosition() {
					return viewportPosition;
				}

				@Override
				public AbsolutePosition getScreenPosition() {
					return screenPosition;
				}

			}, getWindowSize(windowId));
	}

	private static int fixButton(int button) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			button = MouseConstants.LEFT_BUTTON;
		} else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			button = MouseConstants.RIGHT_BUTTON;
		}
		
		return button;
	}
	
	private static int fixAction(int action) {
		if (action == GLFW.GLFW_RELEASE) {
			action = MouseConstants.RELEASE;
		}
		
		return action;
	}
	
	private static AbsoluteSize getWindowSize(long windowId) {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetWindowSize(windowId, width, height);
		
		return new AbsoluteSize((float) width[0], (float) height[0]);
	}
	
}
