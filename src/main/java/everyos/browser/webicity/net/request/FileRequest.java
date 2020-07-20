package everyos.browser.webicity.net.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import everyos.browser.webicity.net.response.IOResponse;
import everyos.browser.webicity.net.response.Response;

public class FileRequest implements Request {
	protected URL url;

	public FileRequest(URL url) {
		this.url = url;
	}

	public static FileRequest create(URL url) {
		return new FileRequest(url);
	}

	@Override public URL getURL() {
		return url;
	}

	@Override public Response send() throws IOException {
		return new IOResponse(new FileInputStream(new File(Optional.of(url.getPath()).orElse("/"))));
	}
}
