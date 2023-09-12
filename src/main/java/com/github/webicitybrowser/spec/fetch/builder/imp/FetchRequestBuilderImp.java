package com.github.webicitybrowser.spec.fetch.builder.imp;

import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.builder.FetchRequestBuilder;
import com.github.webicitybrowser.spec.fetch.imp.FetchRequestImp;
import com.github.webicitybrowser.spec.url.URL;

public class FetchRequestBuilderImp implements FetchRequestBuilder {

	private String method;
	private URL url;

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public void setUrl(URL url) {
		this.url = url;
	}

	@Override
	public FetchRequest build() {
		return new FetchRequestImp(method, url);
	}
	
}
