package com.github.webicitybrowser.webicity.core.net;

import java.io.InputStream;

import com.github.webicitybrowser.spec.url.URL;

public interface Connection {

	InputStream getInputStream();
	
	String getContentType();

	URL getURL();
	
}
