package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.spec.fetch.imp.FetchBodyImp;
import com.github.webicitybrowser.spec.stream.ReadableStream;

/**
 * A FetchBody includes the body of a FetchResponse.
 * It represents the actual content of a resource obtained
 * through some means (e.g. HTTP, file system, etc.).
 */
public interface FetchBody {

	ReadableStream stream();

	Object source();

	static FetchBody createBody(ReadableStream stream, Object source) {
		return new FetchBodyImp(stream, source);
	}

	public static record FetchBodyWithType(FetchBody body, String type) {}

}
