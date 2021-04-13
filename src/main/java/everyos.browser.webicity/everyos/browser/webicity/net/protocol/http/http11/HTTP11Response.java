package everyos.browser.webicity.net.protocol.http.http11;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import everyos.browser.webicity.net.protocol.http.ChunkedInputStream;
import everyos.browser.webicity.net.protocol.http.LimitedInputStream;
import everyos.browser.webicity.net.protocol.http.http11.HTTP11Request.REQUEST_TYPE;

public class HTTP11Response {
	
	//TODO: Support cache
	
	private Map<String, String> headers;
	private int status;
	private InputStream stream;

	public HTTP11Response(int status, Map<String, String> headers, InputStream stream) {
		this.status = status;
		this.headers = headers;
		this.stream = wrapStream(stream);
		
		if (headers.getOrDefault("content-encoding", "identity").equals("gzip")) {
			try {
				this.stream = new GZIPInputStream(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(status);
		headers.forEach((n, v)->System.out.println(n+": "+v));
	}
	
	public int getStatusCode() {
		return status;
	}
	
	public InputStream getInputStream() {
		return stream;
	}
	
	public String getContentType() {
		//TODO
		return headers.getOrDefault("content-type", "text/plain");
	}
	
	public String getAuthenticationChallenge() {
		return headers.get("www-authenticate");
	}
	public REQUEST_TYPE[] getAllow() {
		return null; //TODO
	}
	
	public static enum RESPONSE_TYPE {
		INFORMATIONAL, SUCCESSFUL, REDIRECTION, CLIENT_ERROR, SERVER_ERROR
	}
	
	private InputStream wrapStream(InputStream stream) {
		if (headers.getOrDefault("transfer-encoding", "").equals("chunked")) {
			return new ChunkedInputStream(stream);
		} else if (headers.containsKey("content-length")) {
			return new LimitedInputStream(stream, Integer.valueOf(headers.get("content-length")));
		} else {
			return stream;
		}
	}
}
