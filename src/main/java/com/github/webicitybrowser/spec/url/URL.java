package com.github.webicitybrowser.spec.url;

import java.net.MalformedURLException;

import com.github.webicitybrowser.spec.url.imp.URLImp;

public interface URL {
	
	String getScheme();
	
	String getPath();

	public static URL of(String name) throws InvalidURLException {
		try {
			return new URLImp(new java.net.URL(name));
		} catch (MalformedURLException e) {
			throw new InvalidURLException();
		}
	}
	
	public static URL ofSafe(String name) {
		try {
			return of(name);
		} catch (InvalidURLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
