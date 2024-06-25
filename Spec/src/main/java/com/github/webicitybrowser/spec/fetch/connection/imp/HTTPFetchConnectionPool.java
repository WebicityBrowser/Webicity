package com.github.webicitybrowser.spec.fetch.connection.imp;

import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionInfo;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.http.HTTPService;

public class HTTPFetchConnectionPool implements FetchConnectionPool {

	private final HTTPService httpService;

	public HTTPFetchConnectionPool(HTTPService httpService) {
		this.httpService = httpService;
	}

	@Override
	public FetchConnection createNewConnection(FetchConnectionInfo info) {
		return new HTTPFetchConnectionImp(
			info,
			httpService
		);
	}

	@Override
	public void close() throws Exception {

	}

}
