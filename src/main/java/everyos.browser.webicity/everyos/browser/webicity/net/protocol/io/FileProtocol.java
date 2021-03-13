package everyos.browser.webicity.net.protocol.io;

import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.net.protocol.Protocol;

public class FileProtocol implements Protocol {
	@Override public Request getGenericRequest(URL url) {
		return new FileRequest(url);
	}
}
