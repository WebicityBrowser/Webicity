package com.github.webicitybrowser.spec.http.imp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.spec.http.HTTPFailResponse;
import com.github.webicitybrowser.spec.http.HTTPRequest;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.http.HTTPTransferEncoding;
import com.github.webicitybrowser.spec.http.HTTPTransport;
import com.github.webicitybrowser.spec.http.HTTPTransportFactory;
import com.github.webicitybrowser.spec.http.HTTPVersion;
import com.github.webicitybrowser.spec.http.response.HTTPRedirectResponse;
import com.github.webicitybrowser.spec.http.response.HTTPResponse;
import com.github.webicitybrowser.spec.url.URL;

public class HTTPServiceImp implements HTTPService {
	
	private final String userAgent;
	private final Map<String, HTTPVersion> versions = new HashMap<>();
	private final Map<String, HTTPTransferEncoding> transferEncodings = new HashMap<>();
	
	public HTTPServiceImp(String userAgent) {
		this.userAgent = userAgent;
	}
	
	private HTTPVersion defaultHTTPVersion;
	private HTTPTransportFactory transportFactory;

	@Override
	public void registerHTTPVersion(HTTPVersion version) {
		versions.put(version.getName(), version);
		defaultHTTPVersion = version;;
	}
	
	@Override
	public void setTransportFactory(HTTPTransportFactory transportFactory) {
		this.transportFactory = transportFactory;
	}
	
	@Override
	public void registerTransferEncoding(HTTPTransferEncoding transferEncoding) {
		transferEncodings.put(transferEncoding.getName(), transferEncoding);
	}
	
	@Override
	public HTTPResponse resolveRequest(HTTPRequest request) throws IOException {
		URL url = request.url();
		while (true) {
			// TODO: Re-use transport, and allow client to see redirect text
			// Probably just add an option to the request that allows an
			// early preview of responses.
			// TODO: Save the fragment, unless overriden
			HTTPTransport transport = transportFactory.createTransport(url);
			HTTPResponse response = resolveRequest(url, transport, request);
			if (response instanceof HTTPRedirectResponse redirectResponse) {
				url = redirectResponse.getRedirectURL();
				if (!request.redirectHandler().onRedirectRequest(url)) {
					return (HTTPFailResponse) () -> "Page redirect denied";
				}
			} else {
				return response;
			}
		}
	}

	private HTTPResponse resolveRequest(URL url, HTTPTransport transport, HTTPRequest request) throws IOException {
		String methodName = request.method().toUpperCase();
		switch (methodName) {
		case "GET":
			return defaultHTTPVersion.get(url, createHTTPContext(transport));
		default:
			throw new UnsupportedOperationException("HTTP: Unrecognized method (" + methodName + ")!");
		}
	}

	private HTTPContext createHTTPContext(HTTPTransport transport) {
		return new HTTPContext(userAgent, transport, transferEncodings);
	}

}
