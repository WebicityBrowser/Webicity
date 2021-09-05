package everyos.browser.spec.javadom.imp;

import java.util.ArrayList;
import java.util.function.Consumer;

import everyos.browser.spec.javadom.intf.AddEventListenerOptions;
import everyos.browser.spec.javadom.intf.Event;
import everyos.browser.spec.javadom.intf.EventListener;
import everyos.browser.spec.javadom.intf.EventTarget;
import everyos.browser.spec.javadom.intf.ServiceWorkerGlobalScope;

public class JDEventTarget implements EventTarget {
	//TODO: Implement
	private ArrayList<InternalEventListener> eventListenerList; //TODO: Use Infra List instead
	
	//TODO: Might be tricky to automatically create the wrapping for EventListener, we will have to tell
	//our WebIDL handling library how to do this.
	@Override
	public void addEventListener(String type, EventListener callback, AddEventListenerOptions options) {
		addEventListener(new InternalEventListener(type, callback,
			options.getCapture(), options.getPassive(), options.getOnce()));
	}
	
	@Override
	public void addEventListener(String type, EventListener callback, boolean options) {
		addEventListener(type, callback, new JDAddEventListenerOptions() {
			
		}); //TODO
	}
	
	private void addEventListener(InternalEventListener listener) {
		if (this instanceof ServiceWorkerGlobalScope) {
			//TODO: Additional logic
		}
		if (listener.getCallback()==null) {
			return;
		}
		if (!eventListenerList.contains(listener)) {
			addToListeners(listener);
		}
	}
	
	@Override
	public EventTarget getTheParent(Event event) {
		return null;
	}
	@Override
	public Consumer<Event> getActivationBehaviour() {
		return null;
	}
	
	private void addToListeners(InternalEventListener listeners) {
		if (eventListenerList==null) {
			eventListenerList = new ArrayList<>(1);
		}
	}
}
