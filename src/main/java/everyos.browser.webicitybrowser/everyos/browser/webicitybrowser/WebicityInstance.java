package everyos.browser.webicitybrowser;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.event.EventDispatcher;
import everyos.browser.webicitybrowser.ui.Window;
import everyos.browser.webicitybrowser.ui.event.InstanceMutationEventListener;

public class WebicityInstance {
	private List<Window> windows = new ArrayList<>();
	private EventDispatcher<InstanceMutationEventListener> mutationEventDispatcher = new EventDispatcher<>();
	
	public WebicityInstance() {
		createWindow();
	}
	
	private Window createWindow() {
		Window window = new Window(this);
		windows.add(window);
		mutationEventDispatcher.fire(l->l.onWindowAdded(window));
		return window;
	}

	public void open(URL url) {
		windows.get(0).openTab(url);
	}

	public void start() {
		
	}
	
	public Window[] getWindows() {
		return windows.toArray(new Window[windows.size()]);
	}

	public void addInstanceMutationListener(InstanceMutationEventListener mutationListener) {
		mutationEventDispatcher.addListener(mutationListener);
	}
	public void removeInstanceMutationListener(InstanceMutationEventListener mutationListener) {
		mutationEventDispatcher.removeListener(mutationListener);
	}
}
