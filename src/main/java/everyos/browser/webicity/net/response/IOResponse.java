package everyos.browser.webicity.net.response;

import java.io.IOException;
import java.io.InputStream;

import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicity.renderer.html.HTMLRenderer;

public class IOResponse extends Response {
	protected InputStream stream;

	public IOResponse(InputStream stream) {
		this.stream = stream;
	}

	@Override public int getStatus() {
		return 200;
	}

	@Override
	public InputStream getConnection() throws IOException {
		return stream;
	}

	@Override public Renderer getProbableRenderer() {
		//return new PlainTextRenderer();
		return new HTMLRenderer();
	}
}
