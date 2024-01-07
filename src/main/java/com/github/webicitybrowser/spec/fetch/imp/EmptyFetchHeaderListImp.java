package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchHeaderList;

public class EmptyFetchHeaderListImp implements FetchHeaderList {

	@Override
	public String getHeaderValue(String headerName) {
		return null;
	}
	
}
