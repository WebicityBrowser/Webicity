package everyos.browser.webicitybrowser.ui.event;

import everyos.browser.spec.jnet.URL;
import everyos.browser.webicitybrowser.event.EventListener;

public interface TabMutationEventListener extends EventListener {
	default void onNavigate(URL url) {}
	default void onTitleChange(String name) {}
	void onClose();;
}
