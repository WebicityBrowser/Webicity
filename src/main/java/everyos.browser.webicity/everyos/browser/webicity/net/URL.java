package everyos.browser.webicity.net;

import java.net.MalformedURLException;

public class URL {
	private java.net.URL url;

	public URL(String data) throws MalformedURLException {
		this.url = new java.net.URL(null, data, NullStreamHandler.instance);
	}
	
	public String getProtocol() {
		return url.getProtocol();
	}
	
	public String getHost() {
		return url.getHost();
	}
	
	public String getPath() {
		return url.getPath();
	}
	
	public int getPort() {
		return url.getPort();
	}
	
	public String getFragment() {
		return url.getRef();
	}
	
	public String getQuery() {
		return url.getQuery();
	}
	
	@Override public String toString() {
		return url.toString();
	}
}
