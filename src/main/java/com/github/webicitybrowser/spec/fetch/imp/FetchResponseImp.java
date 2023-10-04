package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchResponse;

public class FetchResponseImp implements FetchResponse {

	private byte[] body;

	public FetchResponseImp(byte[] body) {
		this.body = body;
	}

	@Override
	public byte[] body() {
		return body;
	}
}
