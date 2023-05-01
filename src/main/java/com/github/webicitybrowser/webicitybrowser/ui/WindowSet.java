package com.github.webicitybrowser.webicitybrowser.ui;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicitybrowser.ui.Window.WindowOptions;
import com.github.webicitybrowser.webicitybrowser.ui.event.WindowSetMutationEventListener;

public interface WindowSet {

	void start();
	
	void close();
	
	void open(URL url);
	
	Window[] getWindows();
	
	Window openWindow(WindowOptions options);
	
	void addWindowSetMutationEventListener(WindowSetMutationEventListener mutationListener, boolean sync);
	
	void removeWindowSetMutationEventListener(WindowSetMutationEventListener mutationListener);
	
}
