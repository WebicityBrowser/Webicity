package everyos.browser.webicitybrowser.event;

import java.util.function.Consumer;

public interface EventDispatcher<T extends EventListener> {

	void addListener(T listener);
	
	void removeListener(T listener);
	
	void fire(Consumer<T> eventFunc);
	
}
