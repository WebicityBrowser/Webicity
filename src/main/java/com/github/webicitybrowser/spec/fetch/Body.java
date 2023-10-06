package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.imp.BodyImp;

import java.io.InputStreamReader;

public interface Body {

	InputStreamReader readableStream();

	byte[] source();

	static Body createBody(InputStreamReader isr, byte[] source) {
		return new BodyImp(isr,source);
	}

}
