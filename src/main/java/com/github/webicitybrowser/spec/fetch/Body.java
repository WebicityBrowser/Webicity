package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.imp.BodyImp;

import java.io.Reader;

public interface Body {

	Reader readableStream();

	byte[] source();

	static Body createBody(Reader streamReader, byte[] source) {
		return new BodyImp(streamReader,source);
	}

}
