package com.github.webicitybrowser.spec.stream;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.stream.imp.ReadableStreamDefaultReaderImp;

public interface ReadableStreamDefaultReader extends ReadableStreamReader {
	
	// Extra methods
	void read(ReadRequest request);
	
	void readAllBytes(Consumer<byte[]> consumer, Consumer<Exception> errorConsumer);

	static ReadableStreamDefaultReader acquire(ReadableStream stream) {
		return new ReadableStreamDefaultReaderImp(stream);
	}

}
