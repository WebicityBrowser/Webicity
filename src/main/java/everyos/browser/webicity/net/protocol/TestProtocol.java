package everyos.browser.webicity.net.protocol;

import java.net.URL;

import everyos.browser.webicity.net.request.Request;
import everyos.browser.webicity.net.request.TestRequest;

public class TestProtocol implements Protocol {
	@Override public Request getGenericRequest(URL url) {
		return new TestRequest(url);
	}
}
