package everyos.browser.webicity.net.protocol;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.net.request.Request;

public interface Protocol {
	public Request getGenericRequest(URL url);
}
