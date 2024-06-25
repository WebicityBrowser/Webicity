package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionInfo;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.connection.FetchNetworkPartitionKey;
import com.github.webicitybrowser.spec.htmlbrowsers.Origin;
import com.github.webicitybrowser.spec.url.URL;

public final class FetchConnectionMethods {
	
	private FetchConnectionMethods() {}

	public static FetchConnection obtainConnection(FetchConnectionPool connectionPool, FetchNetworkPartitionKey key, URL url) {
		Origin origin = Origin.of(url);
		return createAConnection(connectionPool, key, origin);
	}

	public static FetchNetworkPartitionKey determineNetworkPartitionKey(FetchRequest request) {
		return null;
	}

	private static FetchConnection createAConnection(FetchConnectionPool connectionPool, FetchNetworkPartitionKey key, Origin origin) {
		FetchConnectionInfo FetchConnectionInfo = new FetchConnectionInfo(key, origin, false);
		return connectionPool.createNewConnection(FetchConnectionInfo);
	}

}
