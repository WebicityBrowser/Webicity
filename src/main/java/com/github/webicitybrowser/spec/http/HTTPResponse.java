package com.github.webicitybrowser.spec.http;

import java.io.InputStream;

public interface HTTPResponse {

	InputStream getInputStream();
	
	HTTPHeaders getHeaders();
	
}
