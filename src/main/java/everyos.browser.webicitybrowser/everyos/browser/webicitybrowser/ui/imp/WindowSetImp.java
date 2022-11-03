package everyos.browser.webicitybrowser.ui.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicitybrowser.event.EventDispatcher;
import everyos.browser.webicitybrowser.event.imp.EventDispatcherImp;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.Window.WindowOptions;
import everyos.browser.webicitybrowser.ui.WindowSet;
import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;
import everyos.browser.webicitybrowser.ui.event.WindowSetMutationEventListener;
import everyos.web.spec.uri.URL;

public class WindowSetImp implements WindowSet {
	
	private final List<Window> windows = new ArrayList<>();
	private final EventDispatcher<WindowSetMutationEventListener> mutationEventDispatcher = new EventDispatcherImp<>();

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
		Tab tab = new TabImp();
		tab.navigate(url);
		windows.get(0).addTab(tab);
	}

	@Override
	public Window[] getWindows() {
		return windows.toArray(new Window[windows.size()]);
	}

	@Override
	public Window openWindow(WindowOptions options) {
		Window window = new WindowImp(options);
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
