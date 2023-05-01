package com.github.webicitybrowser.webicitybrowser.ui.event;

import com.github.webicitybrowser.webicitybrowser.event.EventListener;
import com.github.webicitybrowser.webicitybrowser.ui.Tab;
import com.github.webicitybrowser.webicitybrowser.ui.Window;

public interface WindowMutationEventListener extends EventListener {

	default void onTabAdded(Window window, Tab tab) {}
	
	default void onClose(Window window) {}
	
}
