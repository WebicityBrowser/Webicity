package everyos.engine.ribbon.core.input.mouse;

import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.event.UIEventTarget;

public interface MouseEvent extends UIEvent {
	public static final int LEFT_BUTTON = 1;
	public static final int RIGHT_BUTTON = 2;
	
	public static final int PRESS = 1;
	public static final int RELEASE = 2;
	public static final int DRAG = 3;
	public static final int MOVE = 4;
	
	int getButton();
	int getAction();
	UIEventTarget getEventTarget();
	boolean isExternal();
	int getAbsoluteX();
	int getAbsoluteY();
	int getRelativeX();
	int getRelativeY();
	int getScreenX();
	int getScreenY();
}
