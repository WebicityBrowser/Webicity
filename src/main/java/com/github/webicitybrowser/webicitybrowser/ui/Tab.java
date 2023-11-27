package com.github.webicitybrowser.webicitybrowser.ui;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.ui.Frame;
import com.github.webicitybrowser.webicitybrowser.ui.event.TabMutationEventListener;

/**
 * Represents a browser tab. Provides methods for obtaining
 * the state of the tab and for manipulating the tab.
 */
public interface Tab {
	
	void start();
	
	void close();
	
	/**
	 * Get the name of the tab. This is typically the title of the
	 * primary document shown in the tab.
	 * @return the name of the tab.
	 */
	String getName();
	
	/**
	 * Get the frame that is currently being shown in the tab.
	 * @return the frame that is currently being shown in the tab.
	 */
	Frame getFrame();
	
	/**
	 * Get the URL of the primary document shown in the tab.
	 * @return the URL of the primary document shown in the tab.
	 */
	URL getURL();
	
	/**
	 * Navigate to the given URL - this will load a new document
	 * which will become the primary document shown in the tab.
	 * @param url the URL to navigate to.
	 */
	void navigate(URL url);
	
	/**
	 * Reload the primary document shown in the tab.
	 */
	void reload();
	
	/**
	 * Navigate to the previous document in the tab's history.
	 */
	void back();
	
	/**
	 * Navigate to the next document in the tab's history.
	 */
	void forward();
	
	/**
	 * Add a listener that will be notified when the state of
	 * the tab changes.
	 * @param mutationListener the listener to add.
	 * @param sync whether an initial update (containing the current tab
	 * state) should be sent instantly.
	 */
	void addTabMutationEventListener(TabMutationEventListener mutationListener, boolean sync);
	
	/**
	 * Remove a listener that was previously added using
	 * {@link #addTabMutationEventListener(TabMutationEventListener, boolean)}.
	 * @param mutationListener the listener to remove.
	 */
	void removeTabMutationEventListener(TabMutationEventListener mutationListener);

}
