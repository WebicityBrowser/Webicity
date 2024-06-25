package com.github.webicitybrowser.spec.fetch;

import java.io.InputStream;

import com.github.webicitybrowser.spec.fetch.imp.FetchBodyImp;

/**
 * A FetchBody includes the body of a FetchResponse.
 * It represents the actual content of a resource obtained
 * through some means (e.g. HTTP, file system, etc.).
 * It can hold either a byte array or an InputStream.
 */
public interface FetchBody {

	InputStream readableStream();

	byte[] source();

	static FetchBody createBody(InputStream sourceStream, byte[] sourceArray) {
		if (
			(sourceStream != null && sourceArray != null) ||
			(sourceStream == null && sourceArray == null)
		) {
			throw new IllegalArgumentException("Need exactly one input type");
		}

		return new FetchBodyImp(sourceStream, sourceArray);
	}

}
