package everyos.browser.webicity.net.protocol.io;

import java.io.IOException;
import java.util.Optional;

import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.Response;
import everyos.browser.webicity.net.URL;

public class WebicityRequest implements Request {
	private URL url;

	public WebicityRequest(URL url) {
		this.url = url;
	}

	public static WebicityRequest create(URL url) {
		return new WebicityRequest(url);
	}

	@Override
	public URL getURL() {
		return url;
	}

	@Override
	public Response send() throws IOException {
		String path = Optional.of(url.getHost()).orElse("/");
		String extension = path.indexOf('.')!=-1?"":".html";
		return new IOResponse(ClassLoader.getSystemClassLoader().getResourceAsStream("pages/"+path+extension));
	}
}
