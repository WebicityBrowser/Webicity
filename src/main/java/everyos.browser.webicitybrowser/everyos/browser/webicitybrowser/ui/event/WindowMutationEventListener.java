package everyos.browser.webicitybrowser.ui.event;

import everyos.browser.webicitybrowser.event.EventListener;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.Window;

public interface WindowMutationEventListener extends EventListener {
	default void onTabAdded(Window window, Tab tab) {} //TODO: Boolean focus
	default void onClose(Window window) {};
}
