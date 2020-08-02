package everyos.browser.webicity.net.protocol;

import java.net.URL;

import everyos.browser.webicity.net.request.Request;

public interface Protocol {
	public Request getGenericRequest(URL url);
}
