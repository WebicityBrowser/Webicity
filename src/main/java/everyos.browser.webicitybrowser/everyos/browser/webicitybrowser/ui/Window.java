package everyos.browser.webicitybrowser.ui;

import java.io.Closeable;

import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;

public interface Window extends Closeable {
	
	void start();

	boolean isPrivate();
	
	Tab[] getTabs();
	
	void addTab(Tab tab);
	
	void addWindowMutationEventListener(WindowMutationEventListener mutationListener, boolean sync);
	
	void removeWindowMutationEventListener(WindowMutationEventListener mutationListener);
	
	public static interface WindowOptions {
		
		boolean isPrivate();
		
	}
	
}
