package everyos.browser.webicitybrowser.ui;

import java.io.Closeable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.WebicityInstance;
import everyos.browser.webicitybrowser.event.EventDispatcher;
import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;

public class Window implements Closeable {
	private final WebicityInstance instance;
	private final List<Tab> tabs = new ArrayList<>();
	private EventDispatcher<WindowMutationEventListener> mutationEventDispatcher = new EventDispatcher<>();
	
	public Window(WebicityInstance instance) {
		this.instance = instance;
	}
	
	public void openTab(URL url) {
		Tab tab = new Tab(instance);
		tab.start();
		tab.setURL(url);
		tabs.add(tab);
		mutationEventDispatcher.fire(l->l.onTabAdded(this, tab));
	}
	
	public void openNewTab() {
		try {
			openTab(new URL("https://www.google.com/"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public Tab[] getTabs() {
		return tabs.toArray(new Tab[tabs.size()]);
	}
	

	public void start() {
		
	}
	
	@Override
	public void close() {
		mutationEventDispatcher.fire(l->l.onClose(this));
		for (Tab tab: tabs) {
			tab.close();
		}
	}
	
	public void addWindowMutationListener(WindowMutationEventListener mutationListener) {
		mutationEventDispatcher.addListener(mutationListener);
	}
	public void removeWindowMutationListener(WindowMutationEventListener mutationListener) {
		mutationEventDispatcher.removeListener(mutationListener);
	}
}
