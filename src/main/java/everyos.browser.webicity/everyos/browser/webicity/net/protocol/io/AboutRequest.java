package everyos.browser.webicity.net.protocol.io;

import java.io.IOException;

import everyos.browser.spec.jnet.URL;
import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.Response;

public class AboutRequest implements Request {
	
	private final URL url;

	public AboutRequest(URL url) {
		this.url = url;
	}

	public static AboutRequest create(URL url) {
		return new AboutRequest(url);
	}

	@Override
	public URL getURL() {
		return url;
	}

	@Override
	public Response send() throws IOException {
		return new IOResponse(ClassLoader.getSystemClassLoader().getResourceAsStream("about/" + url.getPath()));
	}
	
}
