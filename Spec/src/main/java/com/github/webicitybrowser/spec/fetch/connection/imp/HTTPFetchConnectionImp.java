package com.github.webicitybrowser.spec.fetch.connection.imp;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.spec.fetch.FetchHeaderList;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.FetchResponse.MessageStream;
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
			return FetchResponse.createNetworkError(request);
		}

		return convertHTTPResponseToFetchResponse(request, response);
	}

	private FetchResponse convertHTTPResponseToFetchResponse(FetchRequest request, HTTPResponse response) {
		if (response instanceof HTTPSuccessResponse successResponse) {
			FetchHeaderList fetchHeaderList = HTTPFetchHeaderListImp.create(successResponse.getHeaders());
			MessageStream messageStream = new MessageStream() {
				private boolean read;

				@Override
				public byte[] read() {
					try {
						read = true;
						return successResponse.getInputStream().readAllBytes();
					} catch (Exception e) {
						logger.error(e.getMessage());
						e.printStackTrace();
						return new byte[0];
					}
				}

				@Override
				public boolean done() {
					return read;
				}
			};

			return new FetchResponseImp(null, request.urlList(), fetchHeaderList) {
				@Override
				public Optional<MessageStream> getMessageStream() {
					return Optional.of(messageStream);
				};
			};
		} else {
			logger.error("Unhandled HTTP response object: " + response);
			return FetchResponse.createNetworkError(request);
		}
	}

}
