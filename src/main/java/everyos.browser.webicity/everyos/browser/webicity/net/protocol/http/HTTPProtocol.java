package everyos.browser.webicity.net.protocol.http;

import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.net.protocol.Protocol;

public class HTTPProtocol implements Protocol {
	@Override public Request getGenericRequest(URL url) {
		return HTTPRequest.create(url);
	}
}
