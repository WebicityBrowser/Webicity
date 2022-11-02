package everyos.browser.webicitybrowser.ui;

import java.io.Closeable;

import everyos.browser.webicitybrowser.ui.event.TabMutationEventListener;
import everyos.web.spec.uri.URL;

public interface Tab extends Closeable {
	
	void start();
	
	String getName();
	
	Frame getFrame();
	
	URL getURL();
	
	void navigate(URL url);
	
	void reload();
	
	void back();
	
	void forward();
	
	void addTabMutationEventListener(TabMutationEventListener mutationListener, boolean sync);
	
	void removeTabMutationEventListener(TabMutationEventListener mutationListener);

}
