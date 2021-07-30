package everyos.browser.webicity.net.protocol.io;

import java.io.IOException;
import java.util.Optional;

import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.Response;
import everyos.browser.webicity.net.URL;

public class AboutRequest implements Request {
	private URL url;

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
		String path = Optional.of(url.getPath()).orElse("/");
		return new IOResponse(ClassLoader.getSystemClassLoader().getResourceAsStream("about/"+path));
	}
}
