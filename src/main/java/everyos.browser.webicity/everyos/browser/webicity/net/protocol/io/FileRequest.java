package everyos.browser.webicity.net.protocol.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import everyos.browser.spec.jnet.URL;
import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.Response;

public class FileRequest implements Request {
	
	private final URL url;

	public FileRequest(URL url) {
		this.url = url;
	}

	public static FileRequest create(URL url) {
		return new FileRequest(url);
	}

	@Override
	public URL getURL() {
		return url;
	}

	@Override
	public Response send() throws IOException {
		return new IOResponse(new FileInputStream(new File(url.getPath())));
	}
	
}
