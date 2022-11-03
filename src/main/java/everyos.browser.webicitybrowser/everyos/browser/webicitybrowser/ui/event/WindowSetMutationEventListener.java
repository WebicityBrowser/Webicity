package everyos.browser.webicitybrowser.ui.event;

import everyos.browser.webicitybrowser.event.EventListener;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.WindowSet;

public interface WindowSetMutationEventListener extends EventListener {

	default void onWindowAdded(WindowSet windowSet, Window window) {}
	
	default void onClose(WindowSet windowSet) {}
	
}
