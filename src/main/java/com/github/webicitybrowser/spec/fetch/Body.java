package com.github.webicitybrowser.spec.fetch;

import java.io.InputStream;

import com.github.webicitybrowser.spec.fetch.imp.BodyImp;

public interface Body {

	InputStream readableStream();

	byte[] source();

	static Body createBody(InputStream inputStreamReader, byte[] source) {
		return new BodyImp(inputStreamReader, source);
	}

}
