package everyos.browser.webicity.net.request;

import java.io.IOException;
import java.net.UnknownHostException;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.net.response.Response;

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
