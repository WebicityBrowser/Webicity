package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchBody;
import com.github.webicitybrowser.spec.fetch.FetchHeaderList;
import com.github.webicitybrowser.spec.fetch.FetchResponse;

public class FetchNetworkError implements FetchResponse {

	@Override
	public FetchBody body() {
		return null;
	}

	@Override
	public FetchHeaderList headerList() {
		return new EmptyFetchHeaderListImp();
	}

}
