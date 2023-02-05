package everyos.desktop.thready.renderer.skija;

import org.lwjgl.glfw.GLFW;

import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.message.MouseConstants;
import everyos.desktop.thready.core.gui.stage.message.MouseMessage;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.AbsolutePositionImp;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;
import everyos.desktop.thready.core.positioning.imp.RectangleImp;

public final class SkijaEventListeners {

	private SkijaEventListeners() {}

	public static void setupEventListeners(long windowId, Unit rootUnit) {
		MessageHandler rootMessageHandler = rootUnit
			.getMessageHandler(getWindowViewport(windowId));
		setupMouseListener(windowId, rootMessageHandler);
	}

	// TODO: Take composite layers into account
	private static void setupMouseListener(long windowId, MessageHandler rootMessageHandler) {
		setupClickCallback(windowId, rootMessageHandler);
		setupMouseMoveCallback(windowId, rootMessageHandler);
	}

	private static void setupClickCallback(long windowId, MessageHandler rootMessageHandler) {
		GLFW.glfwSetMouseButtonCallback(windowId, ($, button, action, mods) -> {
			int mappedButton = fixButton(button);
			int mappedAction = fixAction(action);
			
			double[] x = new double[1], y = new double[1];
			GLFW.glfwGetCursorPos(windowId, x, y);
			
			sendMouseMessage(
				windowId, rootMessageHandler,
				(float) x[0], (float) y[0],
				mappedButton, mappedAction);
		});
	}
	
	private static void setupMouseMoveCallback(long windowId, MessageHandler rootMessageHandler) {
		GLFW.glfwSetCursorPosCallback(windowId, ($, dx, dy) -> {
			float x = (float) dx, y = (float) dy;
			if (GLFW.glfwGetMouseButton(windowId, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {
				sendMouseMessage(windowId, rootMessageHandler, x, y, MouseConstants.LEFT_BUTTON, MouseConstants.DRAG);
			} else if (GLFW.glfwGetMouseButton(windowId, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS) {
				sendMouseMessage(windowId, rootMessageHandler, x, y, MouseConstants.RIGHT_BUTTON, MouseConstants.DRAG);
			} else {
				sendMouseMessage(windowId, rootMessageHandler, x, y, 0, MouseConstants.MOVE);
			}
		});
	}
	
	private static void sendMouseMessage(long windowId, MessageHandler rootMessageHandler, float x, float y, int button, int action) {
		int[] winx = new int[1], winy = new int[1];
		GLFW.glfwGetWindowPos(windowId, winx, winy);
		
		AbsolutePosition viewportPosition = new AbsolutePositionImp(x, y);
		AbsolutePosition screenPosition = new AbsolutePositionImp(winx[0] + x, winy[0] + y);
		
		rootMessageHandler
			.onMessage(new MouseMessage() {	
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

			});
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
	
	private static Rectangle getWindowViewport(long windowId) {
		return new RectangleImp(
			new AbsolutePositionImp(0, 0),
			getWindowSize(windowId));
	}
	
	private static AbsoluteSize getWindowSize(long windowId) {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetWindowSize(windowId, width, height);
		
		return new AbsoluteSizeImp((float) width[0], (float) height[0]);
	}
	
}
