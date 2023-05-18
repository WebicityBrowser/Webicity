package com.github.webicitybrowser.spec.http.response;

import java.io.InputStream;

import com.github.webicitybrowser.spec.http.HTTPHeaders;
import com.github.webicitybrowser.spec.url.URL;

public interface HTTPSuccessResponse extends HTTPResponse {

	URL getURL();
	
	InputStream getInputStream();
	
	HTTPHeaders getHeaders();
	
}
