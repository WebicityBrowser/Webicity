package everyos.browser.webicitybrowser.ui;

import everyos.browser.webicitybrowser.ui.Window.WindowOptions;
import everyos.browser.webicitybrowser.ui.event.WindowSetMutationEventListener;
import everyos.web.spec.uri.URL;

public interface WindowSet {

	void start();
	
	void close();
	
	void open(URL url);
	
	Window[] getWindows();
	
	Window openWindow(WindowOptions options);
	
	void addWindowSetMutationEventListener(WindowSetMutationEventListener mutationListener, boolean sync);
	
	void removeWindowSetMutationEventListener(WindowSetMutationEventListener mutationListener);
	
}
