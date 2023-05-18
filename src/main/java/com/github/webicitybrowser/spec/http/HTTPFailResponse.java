package com.github.webicitybrowser.spec.http;

import com.github.webicitybrowser.spec.http.response.HTTPResponse;

public interface HTTPFailResponse extends HTTPResponse {

	String getReason();
	
}
