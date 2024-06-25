package com.github.webicitybrowser.spec.http.encoding.chunked;

import java.io.InputStream;

import com.github.webicitybrowser.spec.http.HTTPTransferEncoding;

public class ChunkedEncoding implements HTTPTransferEncoding {

	@Override
	public String getName() {
		return "chunked";
	}

	@Override
	public InputStream decode(InputStream inputStream) {
		return new ChunkedInputStream(inputStream);
	}

}
