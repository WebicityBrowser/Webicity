package everyos.browser.webicity.net.response;

import java.io.IOException;
import java.io.InputStream;

import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicity.renderer.test.TestRenderer;

public class TestResponse extends Response {
	protected String path;

	public TestResponse(String path) {
		this.path = path;
	}

	@Override public int getStatus() {
		return 200;
	}

	@Override
	public InputStream getConnection() throws IOException {
		return new InputStream() {
			@Override public int read() throws IOException {
				return -1;
			}
		};
	}

	@Override public Renderer getProbableRenderer() {
		return new TestRenderer(path);
		//return new HTMLRenderer();
	}
}
