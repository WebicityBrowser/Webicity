package com.github.webicitybrowser.spec.fetch.imp;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.fetch.FetchBody;
import com.github.webicitybrowser.spec.fetch.FetchHeaderList;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.url.URL;

public class FetchResponseImp implements FetchResponse {

	private final FetchHeaderList headerList;
	private final List<URL> urlList;

	private FetchBody body;

	public FetchResponseImp(FetchBody body, List<URL> urlList, FetchHeaderList headerList) {
		this.body = body;
		this.urlList = new ArrayList<>(urlList);
		this.headerList = headerList;
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
