package everyos.browser.spec.javadom.intf;

import java.util.function.Consumer;

public interface EventTarget {
	void addEventListener(String type, EventListener callback, AddEventListenerOptions options);
	void addEventListener(String type, EventListener callback, boolean options);
	EventTarget getTheParent(Event event);
	Consumer<Event> getActivationBehaviour();
}
