package everyos.browser.webicity.net.protocol;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.net.request.FileRequest;
import everyos.browser.webicity.net.request.Request;

public class FileProtocol implements Protocol {
	@Override public Request getGenericRequest(URL url) {
		return new FileRequest(url);
	}
}
