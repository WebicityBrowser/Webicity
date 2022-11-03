package everyos.browser.webicitybrowser.ui.event;

import everyos.browser.webicitybrowser.event.EventListener;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.web.spec.uri.URL;

public interface TabMutationEventListener extends EventListener {

	default void onNavigate(Tab tab, URL url) {}
	
	default void onTitleChange(Tab tab, String name) {}
	
	void onClose(Tab tab);
	
}
