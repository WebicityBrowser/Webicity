package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.Body;

import java.io.InputStreamReader;

public class BodyImp implements Body {

	private InputStreamReader isr;
	private byte[] source;
	private int length;

	public BodyImp(InputStreamReader isr, byte[] source, int length) {
		this.isr = isr;
		this.source = source;
		this.length = length;
	}

	public InputStreamReader readableStream() {
		return isr;
	}

	public byte[] source() {
		return source;
	}

	public int length() {
		return length;
	}

}
