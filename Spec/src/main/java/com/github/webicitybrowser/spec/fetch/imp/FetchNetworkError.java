package com.github.webicitybrowser.spec.fetch.imp;

import java.util.List;

import com.github.webicitybrowser.spec.fetch.FetchBody;
import com.github.webicitybrowser.spec.fetch.FetchHeaderList;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.url.URL;

public class FetchNetworkError implements FetchResponse {

	private final List<URL> urlList;

	public FetchNetworkError(List<URL> urlList) {
		this.urlList = urlList;
	}

	@Override
	public FetchBody body() {
		return null;
	}

	@Override
	public URL url() {
		return urlList.get(urlList.size() - 1);
	}

	@Override
	public List<URL> urlList() {
		return urlList;
	}

	@Override
	public void setBody(FetchBody body) {}

	@Override
	public FetchHeaderList headerList() {
		return new EmptyFetchHeaderListImp();
	}

}
