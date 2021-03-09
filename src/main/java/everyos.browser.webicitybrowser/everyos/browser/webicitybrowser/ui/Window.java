package everyos.browser.webicitybrowser.ui;

import java.io.Closeable;
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
		Tab tab = Tab.fromURL(instance, url);
		tabs.add(tab);
		mutationEventDispatcher.fire(l->l.onTabAdded(tab));
	}
	
	public Tab[] getTabs() {
		return tabs.toArray(new Tab[tabs.size()]);
	}
	
	@Override
	public void close() {
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
