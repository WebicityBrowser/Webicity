package everyos.browser.webicitybrowser.ui;

import java.io.Closeable;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.WebicityInstance;
import everyos.browser.webicitybrowser.event.EventDispatcher;
import everyos.browser.webicitybrowser.ui.event.TabMutationEventListener;

public class Tab implements Closeable {
	private URL url;
	private EventDispatcher<TabMutationEventListener> mutationEventDispatcher = new EventDispatcher<>();

	public Tab(WebicityInstance instance, URL url) {
		this.url = url;
	}

	public static Tab fromURL(WebicityInstance instance, URL url) {
		return new Tab(instance, url);
	}

	@Override
	public void close() {
		
	}

	public String getName() {
		//return "New tab";
		return url.getHost();
	}

	public URL getURL() {
		return url;
	}

	public void addTabMutationListener(TabMutationEventListener mutationListener) {
		mutationEventDispatcher.addListener(mutationListener);
	}
	
	public void removeTabMutationListener(TabMutationEventListener mutationListener) {
		mutationEventDispatcher.removeListener(mutationListener);
	}
}
