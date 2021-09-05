package everyos.browser.webicitybrowser.ui;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.javadom.intf.Document;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.WebicityInstance;
import everyos.browser.webicitybrowser.event.EventDispatcher;
import everyos.browser.webicitybrowser.ui.event.WindowMutationEventListener;

public class Window implements Closeable {
	private final WebicityInstance instance;
	private final boolean isPrivateWindow;
	private final List<Tab> tabs;
	private final EventDispatcher<WindowMutationEventListener> mutationEventDispatcher;
	
	public Window(WebicityInstance instance, boolean isPrivateWindow) {
		this.instance = instance;
		this.isPrivateWindow = isPrivateWindow;
		
		this.tabs = new ArrayList<>();
		this.mutationEventDispatcher = new EventDispatcher<>();
	}
	
	public void start() {
		
	}
	
	public void openTab(URL url) {
		Tab tab = new Tab(instance);
		tab.start();
		tab.setURL(url);
		tabs.add(tab);
		mutationEventDispatcher.fire(l->l.onTabAdded(this, tab));
	}
	
	public void openNewTab() {
		openTab(URL.ofSafe("https://www.google.com/"));
	}
	
	public Tab[] getTabs() {
		return tabs.toArray(new Tab[tabs.size()]);
	}
	
	public WebicityInstance getApplicationInstance() {
		return instance;
	}
	
	public boolean isPrivateWindow() {
		return this.isPrivateWindow;
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

	//TODO: Should this exist?
	public void setAssociatedDocument(Document document) {
		
	}
}
