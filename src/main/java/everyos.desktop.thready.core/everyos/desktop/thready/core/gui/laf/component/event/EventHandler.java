package everyos.desktop.thready.core.gui.laf.component.event;

import everyos.desktop.thready.core.gui.event.Event;

public interface EventHandler {

	EventHandlerResult onEvent(Event event);
	
}
