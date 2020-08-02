package everyos.browser.webicity.net.request;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.SSLSocketFactory;

import everyos.browser.webicity.net.response.HTTPResponse;
import everyos.browser.webicity.net.response.Response;

public class HTTPRequest implements Request {
	protected URL url;
	protected String method;
	protected HashMap<String, String> headers;

	public HTTPRequest(URL url) {
		this.url = url;
		this.method = "GET";
		this.headers = new HashMap<>();
		
		headers.put("User-Agent", "Webicity");
		headers.put("Host", url.getHost());
	}

	public static HTTPRequest create(URL url) {
		return new HTTPRequest(url);
	}

	@Override public URL getURL() {
		return url;
	}

	@Override public Response send() throws UnknownHostException, IOException {
		int port = url.getPort();
		if (port==-1) port=url.getProtocol().equals("https")?443:80;
		Socket sock = port==443?
			SSLSocketFactory.getDefault().createSocket(url.getHost(), port):
			new Socket(url.getHost(), port);
		OutputStream stream = sock.getOutputStream();
		
		stream.write((method+" "+url.getPath()+" HTTP/1.1\r\n").getBytes());
		Iterator<String> it = headers.keySet().iterator();
		while (it.hasNext()) {
			String name = it.next();
			stream.write((name+": "+headers.get(name)+"\r\n").getBytes());
		};
		stream.write(("\r\n").getBytes());
		stream.flush();
		
		sock.setSoTimeout(10000);
		
		return new HTTPResponse(sock);
	}
}
