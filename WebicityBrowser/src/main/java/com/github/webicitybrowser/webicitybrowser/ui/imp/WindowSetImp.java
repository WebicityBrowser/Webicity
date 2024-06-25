package com.github.webicitybrowser.webicitybrowser.ui.imp;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.event.EventDispatcher;
import com.github.webicitybrowser.webicity.event.imp.EventDispatcherImp;
import com.github.webicitybrowser.webicitybrowser.BrowserInstance;
import com.github.webicitybrowser.webicitybrowser.ui.Window;
import com.github.webicitybrowser.webicitybrowser.ui.Window.WindowOptions;
import com.github.webicitybrowser.webicitybrowser.ui.WindowSet;
import com.github.webicitybrowser.webicitybrowser.ui.event.WindowMutationEventListener;
import com.github.webicitybrowser.webicitybrowser.ui.event.WindowSetMutationEventListener;

public class WindowSetImp implements WindowSet {
	
	private final BrowserInstance browserInstance;
	
	private final List<Window> windows = new ArrayList<>();
	private final EventDispatcher<WindowSetMutationEventListener> mutationEventDispatcher = new EventDispatcherImp<>();

	public WindowSetImp(BrowserInstance browserInstance) {
		this.browserInstance = browserInstance;
	}
	
	@Override
	public void start() {
		
	}
	
	@Override
	public void close() {
		for (Window window: windows) {
			window.close();
		}
		mutationEventDispatcher.fire(listener -> listener.onClose(this));
	}

	@Override
	public void open(URL url) {
		windows.get(0).openTab().navigate(url);
	}

	@Override
	public Window[] getWindows() {
		return windows.toArray(new Window[windows.size()]);
	}

	@Override
	public Window openWindow(WindowOptions options) {
		Window window = new WindowImp(browserInstance, options);
		window.addWindowMutationEventListener(new WindowCleanupListener(), false);
		windows.add(window);
		mutationEventDispatcher.fire(listener -> listener.onWindowAdded(this, window));
		
		return window;
	}

	@Override
	public void addWindowSetMutationEventListener(WindowSetMutationEventListener mutationListener, boolean sync) {
		mutationEventDispatcher.addListener(mutationListener);
		if (sync) {
			syncListener(mutationListener);
		}
	}

	@Override
	public void removeWindowSetMutationEventListener(WindowSetMutationEventListener mutationListener) {
		mutationEventDispatcher.removeListener(mutationListener);
	}
	
	private void syncListener(WindowSetMutationEventListener mutationListener) {
		for (Window window: windows) {
			mutationListener.onWindowAdded(this, window);
		}
	}
	
	private class WindowCleanupListener implements WindowMutationEventListener {
		
		@Override
		public void onClose(Window window) {
			windows.remove(window);
		}
		
	}

}
