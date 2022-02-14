package everyos.browser.spec.jnet;

import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import everyos.browser.spec.jhtml.browsing.Origin;

public class URL {
	
	private static final URLStreamHandler nullStreamHandler;
	static {
		nullStreamHandler = new URLStreamHandler() {
			@Override
			protected URLConnection openConnection(java.net.URL url) {
				return null;
			}
		};
	}
	
	public static final URL ABOUT_BLANK = URL.ofSafe("about:blank");
	
	private final java.net.URL url;

	public URL(String href) throws MalformedURLException {
		this.url = new java.net.URL(null, href, nullStreamHandler);
	}
	
	public URL(URL origin, String href) throws MalformedURLException {
		java.net.URL url;
		try {
			url = new java.net.URL(null, href, nullStreamHandler);
		} catch (MalformedURLException e) {
			url = new java.net.URL(new java.net.URL(origin.toString()), href);
		}
		
		this.url = url;
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
		return
			o instanceof URL &&
			o.toString().equals(toString());
	}
	
	public static URL ofSafe(String name) {
		try {
			return new URL(name);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
