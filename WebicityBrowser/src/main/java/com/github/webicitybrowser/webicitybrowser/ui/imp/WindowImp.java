package com.github.webicitybrowser.webicitybrowser.ui.imp;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.webicity.event.EventDispatcher;
import com.github.webicitybrowser.webicity.event.imp.EventDispatcherImp;
import com.github.webicitybrowser.webicitybrowser.BrowserInstance;
import com.github.webicitybrowser.webicitybrowser.ui.Tab;
import com.github.webicitybrowser.webicitybrowser.ui.Window;
import com.github.webicitybrowser.webicitybrowser.ui.event.TabMutationEventListener;
import com.github.webicitybrowser.webicitybrowser.ui.event.WindowMutationEventListener;

public class WindowImp implements Window {

	private final List<Tab> tabs = new ArrayList<>();
	private final EventDispatcher<WindowMutationEventListener> mutationEventDispatcher = new EventDispatcherImp<>();
	private final WindowOptions options;
	private final BrowserInstance browserInstance;

	public WindowImp(BrowserInstance browserInstance, WindowOptions options) {
		this.options = options;
		this.browserInstance = browserInstance;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void close() {
		mutationEventDispatcher.fire(listener -> listener.onClose(this));
	}

	@Override
	public boolean isPrivate() {
		return options.isPrivate();
	}

	@Override
	public Tab[] getTabs() {
		return tabs.toArray(new Tab[tabs.size()]);
	}

	@Override
	public void addTab(Tab tab) {
		tab.addTabMutationEventListener(new TabCleanupListener(), false);
		tabs.add(tab);
		mutationEventDispatcher.fire(listener -> listener.onTabAdded(this, tab));
	}
	
	@Override
	public Tab openTab() {
		Tab tab = new TabImp(browserInstance);
		tabs.add(tab);
		// TODO: Dispatch event
		
		return tab;
	}

	@Override
	public void addWindowMutationEventListener(WindowMutationEventListener mutationListener, boolean sync) {
		mutationEventDispatcher.addListener(mutationListener);
		if (sync) {
			syncListener(mutationListener);
		}
	}

	@Override
	public void removeWindowMutationEventListener(WindowMutationEventListener mutationListener) {
		mutationEventDispatcher.removeListener(mutationListener);
	}
	
	private void syncListener(WindowMutationEventListener mutationListener) {
		for (Tab tab: tabs) {
			mutationListener.onTabAdded(this, tab);
		}
	}

	private class TabCleanupListener implements TabMutationEventListener {
		
		@Override
		public void onClose(Tab tab) {
			tabs.remove(tab);
		}
		
	}
	
}
