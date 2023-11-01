package com.github.webicitybrowser.spec.fetch.imp;

import java.io.InputStream;

import com.github.webicitybrowser.spec.fetch.Body;

public class BodyImp implements Body {

	private InputStream bodyStream;
	private byte[] bodyBytes;

	public BodyImp(InputStream inputStream, byte[] source) {
		this.bodyStream = inputStream;
		this.bodyBytes = source;
	}

	public InputStream readableStream() {
		return bodyStream;
	}

	public byte[] source() {
		return bodyBytes;
	}

}
