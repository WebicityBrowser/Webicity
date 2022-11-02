package everyos.browser.webicitybrowser.ui.event;

import everyos.browser.webicitybrowser.event.EventListener;
import everyos.browser.webicitybrowser.ui.Window;

public interface WindowSetMutationEventListener extends EventListener {

	default void onWindowAdded(Window window) {};
	
}
