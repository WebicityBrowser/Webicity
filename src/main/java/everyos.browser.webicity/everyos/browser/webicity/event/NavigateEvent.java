package everyos.browser.webicity.event;

import everyos.browser.webicity.net.URL;

public class NavigateEvent {
	private URL url;

	public NavigateEvent(URL url) {
		this.url = url;
	}
	
	public URL getURL() {
		return url;
	}
}
