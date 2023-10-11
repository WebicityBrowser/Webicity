package com.github.webicitybrowser.spec.fetch.connection.imp;

import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionInfo;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.http.HTTPService;

public class HTTPFetchConnectionPool implements FetchConnectionPool {

	@Override
	public FetchConnection createNewConnection(FetchConnectionInfo info) {
		return new FetchConnectionImp(
			info,
			HTTPService.create("Webicity/0.1.0 ThreadyWeb/0.1.0 Firefox/113.0 (Not actually Firefox)")
		);
	}

	@Override
	public void close() throws Exception {

	}

}
