package everyos.browser.spec.javadom.intf;

import everyos.browser.spec.javadom.intf.inf.ActivationBehavior;

public interface EventTarget {
	void addEventListener(String type, EventListener callback, AddEventListenerOptions options);
	void addEventListener(String type, EventListener callback, boolean options);
	EventTarget getTheParent(Event event);
	
	ActivationBehavior getActivationBehavior();
}
