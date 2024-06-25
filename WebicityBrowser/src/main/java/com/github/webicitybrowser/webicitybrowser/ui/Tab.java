package com.github.webicitybrowser.webicitybrowser.ui;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.ui.Frame;
import com.github.webicitybrowser.webicitybrowser.ui.event.TabMutationEventListener;

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
