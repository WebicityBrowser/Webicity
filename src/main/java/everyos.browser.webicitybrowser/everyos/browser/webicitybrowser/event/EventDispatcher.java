package everyos.browser.webicitybrowser.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventDispatcher<T extends EventListener> {
	List<T> listeners = new ArrayList<>();
	
	public void addListener(T listener) {
		listeners.add(listener);
	}
	
	public void removeListener(Object key) {
		listeners.remove(key);
	}
	
	public void fire(Consumer<T> handler) {
		for (T listener: listeners) {
			handler.accept(listener);
		}
	}
}
