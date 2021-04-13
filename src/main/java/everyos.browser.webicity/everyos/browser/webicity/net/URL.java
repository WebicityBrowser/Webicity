package everyos.browser.webicity.net;

import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import everyos.browser.jhtml.browsing.Origin;

public class URL {
	public static final URL ABOUT_BLANK;
	
	private static final URLStreamHandler nullStreamHandler;
	
	static {
		nullStreamHandler = new URLStreamHandler() {
			@Override protected URLConnection openConnection(java.net.URL u) {
				return null;
			}
		};
		
		URL url = null;
		try {
			url = new URL("about:blank");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ABOUT_BLANK = url;
	}
	
	private java.net.URL url;

	public URL(String href) throws MalformedURLException {
		this.url = new java.net.URL(null, href, nullStreamHandler);
	}
	
	public URL(URL origin, String href) throws MalformedURLException {
		try {
			this.url = new java.net.URL(null, href, nullStreamHandler);
		} catch (MalformedURLException e) {
			this.url = new java.net.URL(new java.net.URL(origin.toString()), href);
		}
	}
	
	public static URL ofSafe(String name) {
		try {
			return new URL(name);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
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
	
	public Origin getOrigin() {
		return null;
	}
	
	@Override
	public String toString() {
		return url.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof URL)) return false;
		return o.toString().equals(toString());
	}
}
