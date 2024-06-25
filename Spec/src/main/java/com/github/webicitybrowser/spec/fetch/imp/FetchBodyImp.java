package com.github.webicitybrowser.spec.fetch.imp;

import java.io.InputStream;

import com.github.webicitybrowser.spec.fetch.FetchBody;

public class FetchBodyImp implements FetchBody {

	private InputStream bodyStream;
	private byte[] bodyBytes;

	public FetchBodyImp(InputStream inputStream, byte[] source) {
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
