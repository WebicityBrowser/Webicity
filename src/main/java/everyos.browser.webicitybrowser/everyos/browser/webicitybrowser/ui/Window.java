package everyos.browser.webicitybrowser.ui;

import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;

public interface Window {
	
	void start();
	
	void close();

	boolean isPrivate();
	
	Tab[] getTabs();
	
	void addTab(Tab tab);
	
	void addWindowMutationEventListener(WindowMutationEventListener mutationListener, boolean sync);
	
	void removeWindowMutationEventListener(WindowMutationEventListener mutationListener);
	
	public static interface WindowOptions {
		
		boolean isPrivate();
		
	}
	
}
