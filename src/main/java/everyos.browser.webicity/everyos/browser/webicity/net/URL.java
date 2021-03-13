package everyos.browser.webicity.net;

import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class URL {
	private java.net.URL url;

	public URL(String data) throws MalformedURLException {
		this.url = new java.net.URL(null, data, nullStreamHandler);
	}
	
	public URL(URL origin, String href) throws MalformedURLException {
		this.url = new java.net.URL(new java.net.URL(origin.toString()), href);
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
	
	@Override
	public String toString() {
		return url.toString();
	}
	
	private static URLStreamHandler nullStreamHandler = new URLStreamHandler() {
		@Override protected URLConnection openConnection(java.net.URL u) {
			return null;
		}
	};

}
