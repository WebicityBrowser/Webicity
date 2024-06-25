package com.github.webicitybrowser.spec.http;

import com.github.webicitybrowser.spec.http.HTTPHeaders.HeaderEntry;
import com.github.webicitybrowser.spec.http.imp.HTTPHeadersImp;

public interface HTTPHeaders extends Iterable<HeaderEntry> {
	
	boolean has(String headerName);

	String get(String headerName);
	
	String set(String headerName, String headerValue);
	
	public static record HeaderEntry(String headerName, String headerValue) {};
	
	public static HTTPHeaders create() {
		return new HTTPHeadersImp();
	}
	
}
