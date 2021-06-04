package everyos.browser.webicity.net.protocol.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import everyos.browser.webicity.net.Response;
import everyos.browser.webicity.net.protocol.http.http11.HTTP11Response;
import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicity.renderer.html.HTMLRenderer;
import everyos.browser.webicity.renderer.plaintext.PlainTextRenderer;

public class HTTPResponse implements Response {
	private HTTP11Response response;

	public HTTPResponse(HTTPSocket socket) throws IOException {
		this.response = socket.getResponse();
		socket.setBlocking(false);
	}

	@Override public Renderer getProbableRenderer() {
		String type = response.getContentType();
		if (type.indexOf(';')!=-1) type = type.substring(0, type.indexOf(';'));
		switch(type) {//TODO: Move this to a registry
			case "text/html":
				return new HTMLRenderer();	
			default:
				return new PlainTextRenderer();
		}
	}

	@Override public int getStatus() {
		return response.getStatusCode();
	}

	@Override public InputStream getConnection() throws UnsupportedEncodingException {
		return response.getInputStream();
	}
}
