package com.github.webicitybrowser.spec.fetch.imp;

import java.util.Map;

import com.github.webicitybrowser.spec.fetch.FetchHeaderList;

public class MapFetchHeaderList implements FetchHeaderList {

	private final Map<String, String> headers;

	public MapFetchHeaderList(Map<String, String> headers) {
		this.headers = headers;
	}

	@Override
	public String getHeaderValue(String headerName) {
		return headers.get(headerName);
	}

	public static FetchHeaderList create(Map<String, String> headers) {
		return new MapFetchHeaderList(headers);
	}

}
