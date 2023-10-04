package com.github.webicitybrowser.spec.fetch.builder.imp;

import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.builder.FetchResponseBuilder;
import com.github.webicitybrowser.spec.fetch.imp.FetchResponseImp;

public class FetchResponseBuilderImp implements FetchResponseBuilder {

	private byte[] body;



	@Override
	public FetchResponse build() {
		return new FetchResponseImp(body);
	}

	@Override
	public void setBody(byte[] body) {
		this.body = body;
	}

}
