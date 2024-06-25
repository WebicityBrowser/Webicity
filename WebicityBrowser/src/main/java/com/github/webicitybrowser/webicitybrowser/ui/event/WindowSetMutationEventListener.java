package com.github.webicitybrowser.webicitybrowser.ui.event;

import com.github.webicitybrowser.webicity.event.EventListener;
import com.github.webicitybrowser.webicitybrowser.ui.Window;
import com.github.webicitybrowser.webicitybrowser.ui.WindowSet;

public interface WindowSetMutationEventListener extends EventListener {

	default void onWindowAdded(WindowSet windowSet, Window window) {}
	
	default void onClose(WindowSet windowSet) {}
	
}
