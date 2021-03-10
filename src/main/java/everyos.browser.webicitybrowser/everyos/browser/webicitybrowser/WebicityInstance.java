package everyos.browser.webicitybrowser;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicity.WebicityEngine;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.event.EventDispatcher;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.event.InstanceMutationEventListener;
import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;

public class WebicityInstance {
	private List<Window> windows = new ArrayList<>();
	private EventDispatcher<InstanceMutationEventListener> mutationEventDispatcher = new EventDispatcher<>();
	private final WebicityEngine engine;
	private WindowMutationListener windowMutationListener;
	
	public WebicityInstance() {
		this.windowMutationListener = new WindowMutationListener();
		this.engine = createEngine();
		createWindow();
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

	private Window createWindow() {
		Window window = new Window(this);
		windows.add(window);
		window.addWindowMutationListener(windowMutationListener);
		mutationEventDispatcher.fire(l->l.onWindowAdded(window));
		return window;
	}
	
	private class WindowMutationListener implements WindowMutationEventListener {
		@Override
		public void onClose(Window window) {
			windows.remove(window);
			window.removeWindowMutationListener(this);
			if (windows.size()==0) {
				engine.quit();
			}
		}
	}
}
