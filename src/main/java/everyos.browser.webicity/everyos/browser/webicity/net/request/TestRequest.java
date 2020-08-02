package everyos.browser.webicity.net.request;

import java.io.IOException;
import java.net.URL;

import everyos.browser.webicity.net.response.Response;
import everyos.browser.webicity.net.response.TestResponse;

public class TestRequest implements Request {
	protected URL url;

	public TestRequest(URL url) {
		this.url = url;
	}

	public static TestRequest create(URL url) {
		return new TestRequest(url);
	}

	@Override public URL getURL() {
		return url;
	}

	@Override public Response send() throws IOException {
		return new TestResponse(url.getHost());
	}
}
