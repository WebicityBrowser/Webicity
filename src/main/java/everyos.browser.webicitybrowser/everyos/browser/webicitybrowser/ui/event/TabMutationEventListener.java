package everyos.browser.webicitybrowser.ui.event;

import everyos.browser.webicitybrowser.event.EventListener;
import everyos.web.spec.uri.URL;

public interface TabMutationEventListener extends EventListener {

	default void onNavigate(URL url) {}
	
	default void onTitleChange(String name) {}
	
	void onClose();;
	
}
