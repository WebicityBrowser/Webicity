package everyos.browser.webicitybrowser.ui;

import java.awt.Window;
import java.io.Closeable;

import everyos.browser.webicitybrowser.ui.Window.WindowOptions;
import everyos.browser.webicitybrowser.ui.event.WindowSetMutationEventListener;
import everyos.web.spec.uri.URL;

public interface WindowSet extends Closeable {

	void start();
	
	void open(URL url);
	
	Window[] getWindows();
	
	Window openWindow(WindowOptions options);
	
	void addWindowSetMutationEventListener(WindowSetMutationEventListener mutationListener, boolean sync);
	
	void removeWindowSetMutationEventListener(WindowSetMutationEventListener mutationListener);
	
}
