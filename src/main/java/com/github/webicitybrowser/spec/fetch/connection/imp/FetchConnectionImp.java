package com.github.webicitybrowser.spec.fetch.connection.imp;

import com.github.webicitybrowser.spec.fetch.Body;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionInfo;
import com.github.webicitybrowser.spec.fetch.imp.FetchResponseImp;
import com.github.webicitybrowser.spec.http.HTTPRequest;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.http.response.HTTPResponse;
import com.github.webicitybrowser.spec.http.response.HTTPSuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;

public class FetchConnectionImp implements FetchConnection {

	private final static Logger logger = LoggerFactory.getLogger(FetchConnectionImp.class);

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
		HTTPResponse response = null;
		try {
			response = httpService.resolveRequest(new HTTPRequest(request.url(), request.method(), redirectURL -> true));
		} catch(Exception e) {
			logger.error(e.getClass().toString());
			return FetchResponse.createNetworkError();
		}

		return convertHTTPResponseToFetchResponse(response);
	}

	@SuppressWarnings("resource")
	private FetchResponse convertHTTPResponseToFetchResponse(HTTPResponse response) {
		if (response instanceof HTTPSuccessResponse successResponse) {
			return new FetchResponseImp(Body.createBody(new InputStreamReader(successResponse.getInputStream()),  new byte[] {}));
		} else {
			logger.error("Unhandled HTTP response object: " + response);
			return FetchResponse.createNetworkError();
		}
	}

}
