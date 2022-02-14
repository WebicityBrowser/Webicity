package everyos.browser.webicity.net.protocol.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import everyos.browser.spec.jnet.http.HTTPSocket;
import everyos.browser.spec.jnet.http.http11.HTTP11Response;
import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.net.Response;
import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicity.renderer.html.HTMLRenderer;
import everyos.browser.webicity.renderer.plaintext.PlainTextRenderer;

public class HTTPResponse implements Response {
	
	private final HTTP11Response response;

	public HTTPResponse(HTTPSocket socket) throws IOException {
		this.response = socket.getResponse();
	}

	@Override
	public Renderer getProbableRenderer() {
		if (response.getStatusCode() >= 300 && response.getStatusCode() < 400) {
			return new RedirectRenderer(response.getHeader("location"));
		}
		
		String type = response.getContentType();
		if (type.indexOf(';') != -1) {
			type = type.substring(0, type.indexOf(';'));
		}
		
		switch(type) {//TODO: Move this to a registry
			case "text/html":
				return new HTMLRenderer();	
			default:
				return new PlainTextRenderer();
		}
	}

	@Override
	public int getStatus() {
		return response.getStatusCode();
	}

	@Override
	public InputStream getConnection() throws UnsupportedEncodingException {
		return response.getInputStream();
	}
	
	private static class RedirectRenderer implements Renderer {
		private String url;

		public RedirectRenderer(String url) {
			this.url = url;
		}

		@Override
		public void execute(WebicityFrame frame, InputStream stream) throws IOException {
			frame.browse(url);
		}

		@Override
		public String getTitle() {
			return "Redirecting you...";
		}

		@Override
		public void addReadyHook(Runnable hook) {
			
		}
	}
	
}
