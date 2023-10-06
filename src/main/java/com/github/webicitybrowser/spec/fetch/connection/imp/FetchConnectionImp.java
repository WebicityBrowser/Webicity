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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.UnresolvedAddressException;

public class FetchConnectionImp implements FetchConnection {

	private final FetchConnectionInfo info;
	private final HTTPService httpService;
	private final static Logger logger = LoggerFactory.getLogger(FetchConnectionImp.class);

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
			response = httpService.resolveRequest(HTTPRequest.createRequest(request.url(), context));
		} catch(Exception e) {
			logger.error(e.getClass().toString());
			return FetchResponse.createNetworkError();
		}

		if (response instanceof HTTPSuccessResponse successResponse) {
			return convertHTTPResponseToFetchResponse(successResponse);
		} else {
			logger.error("Unhandled HTTP response object: " + response);
			return FetchResponse.createNetworkError();
		}
	}

	private FetchResponse convertHTTPResponseToFetchResponse(HTTPSuccessResponse response) {
		return new FetchResponseImp(Body.createBody(new InputStreamReader(response.getInputStream()),  new byte[] {}, -1));
	}

}
