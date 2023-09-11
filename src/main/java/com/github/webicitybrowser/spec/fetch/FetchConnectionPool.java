package com.github.webicitybrowser.spec.fetch;

public interface FetchConnectionPool extends AutoCloseable {

	FetchConnection createNewConnection(FetchConnectionInfo info);
	
}
