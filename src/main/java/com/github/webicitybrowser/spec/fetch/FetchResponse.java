package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.imp.FetchNetworkError;

public interface FetchResponse {

	Body body();

	static FetchResponse createNetworkError() {
		return new FetchNetworkError();
	}

}
