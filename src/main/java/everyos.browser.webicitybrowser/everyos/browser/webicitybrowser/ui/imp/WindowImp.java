package everyos.browser.webicitybrowser.ui.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicitybrowser.event.EventDispatcher;
import everyos.browser.webicitybrowser.event.imp.EventDispatcherImp;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.event.TabMutationEventListener;
import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;

public class WindowImp implements Window {

	private final List<Tab> tabs = new ArrayList<>();
	private final EventDispatcher<WindowMutationEventListener> mutationEventDispatcher = new EventDispatcherImp<>();
	private final WindowOptions options;

	public WindowImp(WindowOptions options) {
		this.options = options;
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
