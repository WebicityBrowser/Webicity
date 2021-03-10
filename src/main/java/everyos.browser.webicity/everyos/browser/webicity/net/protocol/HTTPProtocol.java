package everyos.browser.webicity.net.protocol;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.net.request.HTTPRequest;
import everyos.browser.webicity.net.request.Request;

public class HTTPProtocol implements Protocol {
	@Override public Request getGenericRequest(URL url) {
		return HTTPRequest.create(url);
	}
}
