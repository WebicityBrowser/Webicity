package com.github.webicitybrowser.webicity.protocol;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.spec.http.HTTPRedirectHandler;
import com.github.webicitybrowser.spec.http.HTTPRequest;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.http.response.HTTPResponse;
import com.github.webicitybrowser.spec.http.response.HTTPSuccessResponse;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.net.Protocol;
import com.github.webicitybrowser.webicity.core.net.ProtocolContext;

public class HTTPProtocol implements Protocol {

	private final HTTPService httpService;

	public HTTPProtocol(HTTPService httpService) {
		this.httpService = httpService;
	}

	@Override
	public String[] getSchemes() {
		return new String[] { "http", "https" };
	}

	@Override
	public Connection openConnection(URL url, ProtocolContext context) throws IOException {
		HTTPResponse response = httpService.resolveRequest(createRequest(url, context));
		if (response instanceof HTTPSuccessResponse successResponse) {
			return createConnection(successResponse);
		} else {
			throw new UnsupportedOperationException("Unhandled HTTP response object: " + response);
		}
	}

	private HTTPRequest createRequest(URL url, ProtocolContext context) {
		HTTPRedirectHandler redirectHandler = redirectURL -> context.redirectHandler().onRedirectRequest(redirectURL);
		return new HTTPRequest(url, context.action(), redirectHandler);
	}

	private Connection createConnection(HTTPSuccessResponse response) {
		return new Connection() {
			@Override
			public URL getURL() {
				return response.getURL();
			}
			
			@Override
			public InputStream getInputStream() {
				return response.getInputStream();
			}
			
			@Override
			public String getContentType() {
				if (response.getHeaders().has("Content-Type")) {
					return parseContentType(response.getHeaders().get("Content-Type"));
				}
				
				return "text/plain";
			}
		};
	}
	
	private String parseContentType(String field) {
		int semicolonIndex = field.indexOf(';');
		if (semicolonIndex != -1) {
			return field.substring(0, semicolonIndex);
		}
		
		return field;
	}

}
