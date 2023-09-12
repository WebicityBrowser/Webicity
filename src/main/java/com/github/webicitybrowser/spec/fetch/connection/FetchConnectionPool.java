package com.github.webicitybrowser.spec.fetch.connection;

public interface FetchConnectionPool extends AutoCloseable {

	FetchConnection createNewConnection(FetchConnectionInfo info);
	
}
