package everyos.browser.webicitybrowser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import everyos.browser.spec.jnet.URL;
import everyos.browser.webicity.WebicityEngine;
import everyos.browser.webicitybrowser.event.EventDispatcher;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.event.InstanceMutationEventListener;
import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;

public class WebicityInstance {
	
	private final List<Window> windows;
	private final EventDispatcher<InstanceMutationEventListener> mutationEventDispatcher;
	private final WebicityEngine engine;
	private final WindowMutationListener windowMutationListener;
	
	public WebicityInstance(boolean firstWindowIsPrivate) {
		this.windowMutationListener = new WindowMutationListener();
		this.mutationEventDispatcher = new EventDispatcher<>();
		this.engine = createEngine();
		this.windows = Collections.synchronizedList(new ArrayList<>());
		
		createWindow(firstWindowIsPrivate);
	}

	public void open(URL url) {
		windows.get(0).openTab(url);
	}

	public void start() {
		for (Window window: windows) {
			window.start();
		}
	}
	
	public Window[] getWindows() {
		return windows.toArray(new Window[windows.size()]);
	}
	
	public WebicityEngine getEngine() {
		return this.engine;
	}

	public void addInstanceMutationListener(InstanceMutationEventListener mutationListener) {
		mutationEventDispatcher.addListener(mutationListener);
	}
	
	public void removeInstanceMutationListener(InstanceMutationEventListener mutationListener) {
		mutationEventDispatcher.removeListener(mutationListener);
	}
	
	private WebicityEngine createEngine() {
		return new WebicityEngineImp();
	}

	public Window createWindow(boolean isPrivate) {
		Window window = new Window(this, isPrivate);
		windows.add(window);
		window.addWindowMutationListener(windowMutationListener);
		mutationEventDispatcher.fire(l -> l.onWindowAdded(window));
		return window;
	}
	
	private class WindowMutationListener implements WindowMutationEventListener {
		@Override
		public void onClose(Window window) {
			window.removeWindowMutationListener(this);
			
			windows.remove(window);
			if (windows.size() == 0) {
				engine.quit();
			}
		}
	}
	
}
