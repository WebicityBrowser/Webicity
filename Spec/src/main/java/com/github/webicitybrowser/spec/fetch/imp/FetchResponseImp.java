package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchBody;
import com.github.webicitybrowser.spec.fetch.FetchHeaderList;
import com.github.webicitybrowser.spec.fetch.FetchResponse;

public class FetchResponseImp implements FetchResponse {

	private final FetchBody body;
	private final FetchHeaderList headerList;

	public FetchResponseImp(FetchBody body, FetchHeaderList headerList) {
		this.body = body;
		this.headerList = headerList;
	}

	@Override
	public FetchBody body() {
		return body;
	}

	@Override
	public FetchHeaderList headerList() {
		return headerList;
	}

}
