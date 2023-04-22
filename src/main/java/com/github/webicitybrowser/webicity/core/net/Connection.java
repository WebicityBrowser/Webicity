package com.github.webicitybrowser.webicity.core.net;

import java.io.Reader;

import com.github.webicitybrowser.spec.url.URL;

public interface Connection {

	Reader getInputReader();
	
	String getContentType();

	URL getURL();
	
}
