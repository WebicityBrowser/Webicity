package everyos.browser.webicity.event;

import java.net.URL;

public class NavigateEvent {
	private URL url;

	public NavigateEvent(URL url) {
		this.url = url;
	}
	
	public URL getURL() {
		return url;
	}
}
