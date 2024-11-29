package com.github.webicitybrowser.spec.fetch.imp;

import com.github.webicitybrowser.spec.fetch.FetchBody;
import com.github.webicitybrowser.spec.stream.ReadableStream;

public class FetchBodyImp implements FetchBody {

	private ReadableStream stream;
	private Object source;

	public FetchBodyImp(ReadableStream stream, Object source) {
		this.stream = stream;
		this.source = source;
	}

	public ReadableStream stream() {
		return stream;
	}

	public Object source() {
		return source;
	}

}
