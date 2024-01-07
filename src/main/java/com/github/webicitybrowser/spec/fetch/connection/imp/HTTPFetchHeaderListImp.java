package com.github.webicitybrowser.spec.fetch.connection.imp;

import com.github.webicitybrowser.spec.fetch.FetchHeaderList;
import com.github.webicitybrowser.spec.http.HTTPHeaders;

public class HTTPFetchHeaderListImp implements FetchHeaderList {

	private final HTTPHeaders internalHeaders;

	public HTTPFetchHeaderListImp(HTTPHeaders internalHeaders) {
		this.internalHeaders = internalHeaders;
	}

	@Override
	public String getHeaderValue(String headerName) {
		return internalHeaders.get(headerName);
	}

    public static FetchHeaderList create(HTTPHeaders internalHeaders) {
        return new HTTPFetchHeaderListImp(internalHeaders);
    }

}
