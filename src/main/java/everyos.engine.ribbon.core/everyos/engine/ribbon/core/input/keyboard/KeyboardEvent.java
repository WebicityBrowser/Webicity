package everyos.engine.ribbon.core.input.keyboard;

import everyos.engine.ribbon.core.event.UIEvent;

public interface KeyboardEvent extends UIEvent {
	public static final int KEY_PRESS = 1;
	public static final int KEY_RELEASE = 2;
	public static final int KEY_HOLD = 3;
	
	Key getKey();
	int getAction();
}
