package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.Body;

import java.io.InputStreamReader;

public class BodyImp implements Body {

	private InputStreamReader bodyStream;
	private byte[] bodyBytes;

	public BodyImp(InputStreamReader isr, byte[] source) {
		this.bodyStream = isr;
		this.bodyBytes = source;
	}

	public InputStreamReader readableStream() {
		return bodyStream;
	}

	public byte[] source() {
		return bodyBytes;
	}

}
