package everyos.browser.webicitybrowser.ui;

import everyos.browser.webicity.ui.Frame;
import everyos.browser.webicitybrowser.ui.event.TabMutationEventListener;
import everyos.web.spec.uri.URL;

public interface Tab {
	
	void start();
	
	void close();
	
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
