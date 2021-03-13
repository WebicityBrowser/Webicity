package everyos.browser.webicity.net.protocol;

import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.URL;

public interface Protocol {
	public Request getGenericRequest(URL url);
}
