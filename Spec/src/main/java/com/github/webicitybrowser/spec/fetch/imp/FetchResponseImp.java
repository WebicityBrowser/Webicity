package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchBody;
import com.github.webicitybrowser.spec.fetch.FetchHeaderList;
import com.github.webicitybrowser.spec.fetch.FetchResponse;

public class FetchResponseImp implements FetchResponse {

	private final FetchHeaderList headerList;

	private FetchBody body;

	public FetchResponseImp(FetchBody body, FetchHeaderList headerList) {
		this.body = body;
		this.headerList = headerList;
	}

	@Override
	public FetchBody body() {
		return this.body;
	}

	@Override
	public FetchHeaderList headerList() {
		return headerList;
	}

	@Override
	public void setBody(FetchBody body) {
		this.body = body;
	}

}
