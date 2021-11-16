package everyos.engine.ribbon.core.input.mouse;

import everyos.engine.ribbon.core.event.UIEvent;

public interface ScrollEvent extends UIEvent {
	int getX();
	int getY();
}
