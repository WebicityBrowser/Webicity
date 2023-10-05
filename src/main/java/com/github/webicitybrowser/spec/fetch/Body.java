package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.imp.BodyImp;

import java.io.InputStreamReader;

public interface Body {

	InputStreamReader readableStream();
	byte[] source();
	int length();

	static Body createBody(InputStreamReader isr, byte[] source, int length) {
		return new BodyImp(isr,source,length);
	}

}
