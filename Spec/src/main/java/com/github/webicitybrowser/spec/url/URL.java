package com.github.webicitybrowser.spec.url;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import com.github.webicitybrowser.spec.url.imp.URLImp;

public interface URL {

	static final URLStreamHandler NULL_URL_STREAM_HANDLER = new URLStreamHandler() {		
		@Override
		protected URLConnection openConnection(java.net.URL u) throws IOException {
			return null;
		}
	};
	
	String getScheme();
	
	String getHost();
	
	int getPort();
	
	String getPath();
	
	String getQuery();

	public static URL of(String href) throws InvalidURLException {
		try {
			return new URLImp(new java.net.URL(null, href, NULL_URL_STREAM_HANDLER));
		} catch (MalformedURLException e) {
			throw new InvalidURLException();
		}
	}

	public static URL of(URL base, String href) throws InvalidURLException {
		try {
			java.net.URL baseRaw = new java.net.URL(null, base.toString(), NULL_URL_STREAM_HANDLER);
			return new URLImp(new java.net.URL(baseRaw, href, NULL_URL_STREAM_HANDLER));
		} catch (MalformedURLException e) {
			throw new InvalidURLException();
		}
	}
	
	public static URL ofSafe(String href) {
		try {
			return of(href);
		} catch (InvalidURLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
