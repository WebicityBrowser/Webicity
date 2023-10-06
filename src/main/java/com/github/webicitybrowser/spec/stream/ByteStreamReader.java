package com.github.webicitybrowser.spec.stream;

import com.github.webicitybrowser.spec.stream.imp.ByteStreamReaderImp;

import java.io.IOException;
import java.io.InputStreamReader;

public interface ByteStreamReader {

	byte[] readAllBytes(InputStreamReader inputStream) throws IOException;

	static ByteStreamReader createStreamReader() {
		return new ByteStreamReaderImp();
	}

}
