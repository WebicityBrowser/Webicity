package com.github.webicitybrowser.codec.jpeg.util;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;

public final class JPEGUtil {
	
	private JPEGUtil() {}

	public static int readTwoByte(InputStream chunkSection) throws IOException, MalformedJPEGException {
		int byteRead = chunkSection.read() & 0xFF;
		if (byteRead == -1) throw new IOException("Unexpected end of stream");
		int length = byteRead << 8;
		byteRead = chunkSection.read() & 0xFF;
		if (byteRead == -1) throw new IOException("Unexpected end of stream");
		length += byteRead;

		return length;
	}

	public static int read(InputStream inputStream) throws IOException, MalformedJPEGException {
		int ch = inputStream.read();
		if (ch == -1) {
			throw new MalformedJPEGException("Unexpected end of stream");
		}

		return ch;
	}

}
