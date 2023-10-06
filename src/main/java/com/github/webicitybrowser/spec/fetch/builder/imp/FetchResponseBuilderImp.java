package com.github.webicitybrowser.spec.fetch.builder.imp;

import com.github.webicitybrowser.spec.fetch.Body;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.builder.FetchResponseBuilder;
import com.github.webicitybrowser.spec.fetch.imp.FetchResponseImp;

public class FetchResponseBuilderImp implements FetchResponseBuilder {

	private Body body;

	@Override
	public FetchResponse build() {
		return new FetchResponseImp(body);
	}

	@Override
	public void setBody(Body body) {
		this.body = body;
	}

}
