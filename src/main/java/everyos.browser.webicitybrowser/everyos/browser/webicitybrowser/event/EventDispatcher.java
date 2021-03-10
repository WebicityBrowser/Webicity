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
	
	@SuppressWarnings("unchecked")
	public void fire(Consumer<T> handler) {
		for (EventListener c: listeners.toArray(new EventListener[listeners.size()])) {
			handler.accept((T) c);
		}
	}
}
