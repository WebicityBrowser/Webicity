package com.github.webicitybrowser.spec.http.imp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.spec.http.HTTPResponse;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.http.HTTPTransferEncoding;
import com.github.webicitybrowser.spec.http.HTTPTransport;
import com.github.webicitybrowser.spec.http.HTTPTransportFactory;
import com.github.webicitybrowser.spec.http.HTTPVersion;
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
	public HTTPResponse get(URL url) throws IOException {
		HTTPTransport transport = transportFactory.createTransport(url);
		return defaultHTTPVersion.get(url, createHTTPContext(transport));
	}

	private HTTPContext createHTTPContext(HTTPTransport transport) {
		return new HTTPContext(userAgent, transport, transferEncodings);
	}

}
