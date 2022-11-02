package everyos.browser.webicitybrowser.ui.event;

import everyos.browser.webicitybrowser.event.EventListener;
import everyos.browser.webicitybrowser.ui.Tab;

public interface WindowMutationEventListener extends EventListener {

	default void onTabAdded(Tab tab) {}
	
	default void onClose() {};
	
}
