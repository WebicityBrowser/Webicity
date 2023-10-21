package com.github.webicitybrowser.codec.png.imp;

import java.io.IOException;
import java.io.InputStream;

public final class PNGChunkInfoReader {
	
	private PNGChunkInfoReader() {}

	public static PNGChunkInfo read(InputStream inputStream) throws IOException {
		int length = readInt(inputStream);
		byte[] type = readType(inputStream);
		byte[] data = readData(inputStream, length);
		// TODO: Check CRC
		readInt(inputStream);

		return new PNGChunkInfo(type, data);
	}

	private static int readInt(InputStream inputStream) throws IOException {
		int length = 0;
		for (int i = 0; i < 4; i++) {
			length = (length << 8) | readByte(inputStream);
		}

		return length;
	}

	private static byte[] readType(InputStream inputStream) throws IOException {
		byte[] type = new byte[4];
		for (int i = 0; i < 4; i++) {
			type[i] = (byte) inputStream.read();
		}

		return type;
	}

	private static byte[] readData(InputStream inputStream, int length) throws IOException {
		byte[] data = new byte[length];
		for (int i = 0; i < length; i++) {
			data[i] = (byte) inputStream.read();
		}

		return data;
	}

	private static byte readByte(InputStream inputStream) throws IOException {
		int byteRead = inputStream.read();
		if (byteRead == -1) {
			throw new IOException("Unexpected end of stream");
		}

		return (byte) byteRead;
	}

}
