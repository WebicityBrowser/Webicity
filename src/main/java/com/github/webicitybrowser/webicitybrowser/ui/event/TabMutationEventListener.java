package com.github.webicitybrowser.webicitybrowser.ui.event;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicitybrowser.event.EventListener;
import com.github.webicitybrowser.webicitybrowser.ui.Tab;

public interface TabMutationEventListener extends EventListener {

	default void onNavigate(Tab tab, URL url) {}
	
	default void onTitleChange(Tab tab, String name) {}
	
	void onClose(Tab tab);
	
}
