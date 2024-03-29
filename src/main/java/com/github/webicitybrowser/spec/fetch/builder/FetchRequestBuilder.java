package com.github.webicitybrowser.spec.fetch.builder;

import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.builder.imp.FetchRequestBuilderImp;
import com.github.webicitybrowser.spec.url.URL;

public interface FetchRequestBuilder {
	
	void setMethod(String method);

	void setUrl(URL url);

	FetchRequest build();

	static FetchRequestBuilder create() {
		return new FetchRequestBuilderImp();
	}

}
