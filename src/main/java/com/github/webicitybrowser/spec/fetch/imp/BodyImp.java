package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.Body;

import java.io.Reader;

public class BodyImp implements Body {

	private Reader bodyStream;
	private byte[] bodyBytes;

	public BodyImp(Reader isr, byte[] source) {
		this.bodyStream = isr;
		this.bodyBytes = source;
	}

	public Reader readableStream() {
		return bodyStream;
	}

	public byte[] source() {
		return bodyBytes;
	}

}
