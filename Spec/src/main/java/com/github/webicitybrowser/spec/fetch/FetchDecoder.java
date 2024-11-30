package com.github.webicitybrowser.spec.fetch;

public interface FetchDecoder {
	
	byte[] translate(byte[] data);

	default void close() {}
	
}
