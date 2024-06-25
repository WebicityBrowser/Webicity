package com.github.webicitybrowser.webicitybrowser.ui;

import com.github.webicitybrowser.webicitybrowser.ui.event.WindowMutationEventListener;

public interface Window {
	
	void start();
	
	void close();

	boolean isPrivate();
	
	Tab[] getTabs();
	
	void addTab(Tab tab);
	
	Tab openTab();
	
	void addWindowMutationEventListener(WindowMutationEventListener mutationListener, boolean sync);
	
	void removeWindowMutationEventListener(WindowMutationEventListener mutationListener);
	
	public static interface WindowOptions {
		
		boolean isPrivate();
		
	}
	
}
