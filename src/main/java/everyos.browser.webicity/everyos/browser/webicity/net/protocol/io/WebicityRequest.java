package everyos.browser.webicity.net.protocol.io;

import java.io.IOException;

import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.Response;
import everyos.browser.webicity.net.URL;

public class WebicityRequest implements Request {
	private final URL url;

	public WebicityRequest(URL url) {
		this.url = url;
	}

	@Override
	public URL getURL() {
		return url;
	}

	@Override
	public Response send() throws IOException {
		String path = url.getHost();
		String extension = path.indexOf('.')!=-1?"":".html";
		return new IOResponse(ClassLoader.getSystemClassLoader().getResourceAsStream("pages/"+path+extension));
	}
	
	public static WebicityRequest create(URL url) {
		return new WebicityRequest(url);
	}
}
