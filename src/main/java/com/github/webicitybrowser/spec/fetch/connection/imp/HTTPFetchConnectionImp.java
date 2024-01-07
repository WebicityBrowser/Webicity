package com.github.webicitybrowser.spec.fetch.connection.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.spec.fetch.FetchBody;
import com.github.webicitybrowser.spec.fetch.FetchHeaderList;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionInfo;
import com.github.webicitybrowser.spec.fetch.imp.FetchResponseImp;
import com.github.webicitybrowser.spec.http.HTTPRequest;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.http.response.HTTPResponse;
import com.github.webicitybrowser.spec.http.response.HTTPSuccessResponse;

public class HTTPFetchConnectionImp implements FetchConnection {
	
	private final static Logger logger = LoggerFactory.getLogger(HTTPFetchConnectionImp.class);

	private final FetchConnectionInfo info;
	private final HTTPService httpService;

	public HTTPFetchConnectionImp(FetchConnectionInfo fetchConnectionInfo, HTTPService httpService) {
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
			e.printStackTrace();
			return FetchResponse.createNetworkError();
		}

		return convertHTTPResponseToFetchResponse(response);
	}

	@SuppressWarnings("resource")
	private FetchResponse convertHTTPResponseToFetchResponse(HTTPResponse response) {
		if (response instanceof HTTPSuccessResponse successResponse) {
			FetchBody fetchBody = FetchBody.createBody(successResponse.getInputStream(), null);
			FetchHeaderList fetchHeaderList = HTTPFetchHeaderListImp.create(successResponse.getHeaders());
			
			return new FetchResponseImp(fetchBody, fetchHeaderList);
		} else {
			logger.error("Unhandled HTTP response object: " + response);
			return FetchResponse.createNetworkError();
		}
	}

}
