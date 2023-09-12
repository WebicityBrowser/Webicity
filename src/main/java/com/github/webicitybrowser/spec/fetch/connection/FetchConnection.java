package com.github.webicitybrowser.spec.fetch.connection;

import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchResponse;

public interface FetchConnection {
	
	FetchConnectionInfo info();

	FetchResponse send(FetchRequest request);

}
