package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.Body;

import java.io.Reader;

public class BodyImp implements Body {

	private final Reader bodyStream;
	private final byte[] bodyBytes;

	public BodyImp(Reader inputStreamReader, byte[] source) {
		this.bodyStream = inputStreamReader;
		this.bodyBytes = source;
	}

	public Reader readableStream() {
		return bodyStream;
	}

	public byte[] source() {
		return bodyBytes;
	}

}
