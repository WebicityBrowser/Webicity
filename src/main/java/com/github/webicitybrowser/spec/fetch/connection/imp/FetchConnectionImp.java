package com.github.webicitybrowser.spec.fetch.connection.imp;

import com.github.webicitybrowser.spec.fetch.Body;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionInfo;
import com.github.webicitybrowser.spec.fetch.imp.FetchResponseImp;
import com.github.webicitybrowser.spec.http.HTTPRedirectHandler;
import com.github.webicitybrowser.spec.http.HTTPRequest;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.http.response.HTTPResponse;
import com.github.webicitybrowser.spec.http.response.HTTPSuccessResponse;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.ProtocolContext;

import java.io.IOException;
import java.io.InputStreamReader;

public class FetchConnectionImp implements FetchConnection {

	private final FetchConnectionInfo info;
	private final HTTPService httpService;

	public FetchConnectionImp(FetchConnectionInfo fetchConnectionInfo, HTTPService httpService) {
		this.info = fetchConnectionInfo;
		this.httpService = httpService;
	}

	@Override
	public FetchConnectionInfo info() {
		return info;
	}

	@Override
	public FetchResponse send(FetchRequest request) {
		ProtocolContext context = new ProtocolContext(request.method(), redirectURL -> true);
		HTTPResponse response = null;
		try {
			response = httpService.resolveRequest(createRequest(request.url(), context));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (response instanceof HTTPSuccessResponse successResponse) {
			return new FetchResponseImp(Body.createBody(new InputStreamReader(successResponse.getInputStream()),  new byte[]{}, -1));
		} else {
			throw new UnsupportedOperationException("Unhandled HTTP response object: " + response);
		}
	}

	private HTTPRequest createRequest(URL url, ProtocolContext context) {
		HTTPRedirectHandler redirectHandler = redirectURL -> context.redirectHandler().onRedirectRequest(redirectURL);
		return new HTTPRequest(url, context.action(), redirectHandler);
	}

}
