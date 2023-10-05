package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.Body;
import com.github.webicitybrowser.spec.fetch.FetchResponse;

public class FetchResponseImp implements FetchResponse {

	private final Body body;

	public FetchResponseImp(Body body) {
		this.body = body;
	}

	@Override
	public Body body() {
		return body;
	}

}
