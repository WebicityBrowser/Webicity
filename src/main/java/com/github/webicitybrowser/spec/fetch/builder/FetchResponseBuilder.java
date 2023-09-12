package com.github.webicitybrowser.spec.fetch.builder;

import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.builder.imp.FetchResponseBuilderImp;

public interface FetchResponseBuilder {
	
	FetchResponse build();
	
	static FetchResponseBuilder create() {
		return new FetchResponseBuilderImp();
	}

	static FetchResponse createNetworkError() {
		return create().build();
	}

}
