package com.github.webicitybrowser.spec.stream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ByteStreamReader {

	private ByteStreamReader() {}

	public static byte[] readAllBytes(InputStream inputStream) throws IOException {
		InputStream bufferedInputStream = new BufferedInputStream(inputStream, 4 * 1024);
		
		//TODO: Implement spec
		return bufferedInputStream.readAllBytes();
	}

}
