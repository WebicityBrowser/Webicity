package com.github.webicitybrowser.spec.http;

import java.io.IOException;

import com.github.webicitybrowser.spec.http.imp.HTTPContext;
import com.github.webicitybrowser.spec.http.response.HTTPResponse;
import com.github.webicitybrowser.spec.url.URL;

public interface HTTPVersion {
	
	String getName();

	HTTPResponse get(URL url, HTTPContext httpContext) throws IOException;
	
}
