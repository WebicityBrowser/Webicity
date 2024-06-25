package com.github.webicitybrowser.spec.http;

import java.io.IOException;

import com.github.webicitybrowser.spec.http.imp.HTTPServiceImp;
import com.github.webicitybrowser.spec.http.response.HTTPResponse;

public interface HTTPService {

	void setTransportFactory(HTTPTransportFactory transportFactory);
	
	void registerHTTPVersion(HTTPVersion version);
	
	void registerTransferEncoding(HTTPTransferEncoding transferEncoding);
	
	HTTPResponse resolveRequest(HTTPRequest request) throws IOException;
	
	public static HTTPService create(String userAgent) {
		return new HTTPServiceImp(userAgent);
	}
	
}
