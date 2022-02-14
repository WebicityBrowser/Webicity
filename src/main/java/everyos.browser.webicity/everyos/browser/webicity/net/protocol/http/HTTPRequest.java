package everyos.browser.webicity.net.protocol.http;

import java.io.IOException;
import java.net.UnknownHostException;

import everyos.browser.spec.jnet.URL;
import everyos.browser.spec.jnet.http.HTTPSocket;
import everyos.browser.spec.jnet.http.http11.HTTP11Request;
import everyos.browser.webicity.net.Request;
import everyos.browser.webicity.net.Response;

public class HTTPRequest implements Request {
	private URL url;
	private HTTP11Request request;

	public HTTPRequest(URL url) {
		this.url = url;
		this.request = new HTTP11Request();
		
		request.setRequestTarget(url);
		
		// We include Firefox in our UA string because some sites are not served properly without it
		request.setUserAgent("Webicity/0.1.0 (LaceWebExtensions/0.1.0); Firefox/93.0 UA styling preferred");
		request.acceptEncoding("gzip", -1);
		request.acceptEncoding("deflate", -1);
		request.acceptEncoding("br", -1);
		request.acceptLanugage("en-US", -1);
		request.acceptLanugage("en", .9);
		request.acceptCharset("utf-8", -1);
		request.accept("text/html", -1);
		request.accept("text/plain", -1);
		//TODO: Set referrer
	}

	public static HTTPRequest create(URL url) {
		return new HTTPRequest(url);
	}

	@Override
	public URL getURL() {
		return url;
	}

	@Override
	public Response send() throws UnknownHostException, IOException {
		HTTPSocket socket = HTTPSocket.openHTTPSocket(url, true);
		
		socket.sendRequest(request);
		//TODO: Catch SSLHandshakeException and prompt user to disable TLS
		
		return new HTTPResponse(socket);
	}
}
