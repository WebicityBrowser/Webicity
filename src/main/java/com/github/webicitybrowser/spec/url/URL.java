package com.github.webicitybrowser.spec.url;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import com.github.webicitybrowser.spec.url.imp.URLImp;

public interface URL {
	
	String getScheme();
	
	String getHost();
	
	int getPort();
	
	String getPath();

	public static URL of(String href) throws InvalidURLException {
		try {
			return new URLImp(new java.net.URL(null, href, new URLStreamHandler() {		
				@Override
				protected URLConnection openConnection(java.net.URL u) throws IOException {
					return null;
				}
			}));
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
