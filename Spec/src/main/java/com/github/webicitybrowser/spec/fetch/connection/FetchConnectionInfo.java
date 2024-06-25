package com.github.webicitybrowser.spec.fetch.connection;

import com.github.webicitybrowser.spec.htmlbrowsers.Origin;

public record FetchConnectionInfo(FetchNetworkPartitionKey networkPartitionKey, Origin origin, boolean isSecure) {
	
}
