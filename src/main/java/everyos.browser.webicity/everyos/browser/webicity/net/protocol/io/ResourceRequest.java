package everyos.browser.webicity.net.protocol.io;

import java.io.IOException;
import java.net.UnknownHostException;

import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.Response;
import everyos.browser.webicity.net.URL;

public class ResourceRequest implements Request {
	protected URL url;

	public ResourceRequest() {}
	
	public ResourceRequest(URL url) {
		this.url = url;
	}

	public static ResourceRequest create(URL url) {
		return new ResourceRequest(url);
	}

	@Override public URL getURL() {
		return url;
	}

	@Override public Response send() throws UnknownHostException, IOException {
		return null;
	}
}
