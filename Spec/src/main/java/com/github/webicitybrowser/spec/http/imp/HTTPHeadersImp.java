package com.github.webicitybrowser.spec.http.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import com.github.webicitybrowser.spec.http.HTTPHeaders;

public class HTTPHeadersImp implements HTTPHeaders {
	
	private final Map<HeaderKey, String> headers = new HashMap<>();

	@Override
	public Iterator<HeaderEntry> iterator() {
		return headers
			.entrySet()
			.stream()
			.map(entry -> new HeaderEntry(entry.getKey().headerName(), entry.getValue()))
			.iterator();
	}

	@Override
	public boolean has(String headerName) {
		return headers.containsKey(new HeaderKey(headerName));
	}

	@Override
	public String get(String headerName) {
		return headers.get(new HeaderKey(headerName));
	}

	@Override
	public String set(String headerName, String headerValue) {
		return headers.put(new HeaderKey(headerName), headerValue);
	}

	private record HeaderKey(String headerName) {
		
		@Override
		public boolean equals(Object o) {
			return
				o instanceof HeaderKey key &&
				headerName.equalsIgnoreCase(key.headerName());
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(headerName.toLowerCase());
		}
		
	}
	
}
