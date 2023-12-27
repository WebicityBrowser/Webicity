package com.github.webicitybrowser.codec.jpeg.chunk.jfif;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.exception.InvalidJPEGSignatureException;
import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public final class JFIFChunkParser {
	
	private JFIFChunkParser() {}

	public static JFIFChunkInfo read(InputStream chunkSection) throws IOException, MalformedJPEGException {
		int length = readLength(chunkSection);
		if (length < 16) throw new MalformedJPEGException("JFIF chunk length mismatch");
		String identifier = readIdentifier(chunkSection);
		if (!identifier.equals("JFIF\0")) throw new InvalidJPEGSignatureException("Invalid JFIF chunk identifier");
		for (int i = 0; i < length - 7; i++) {
			chunkSection.read();
		}

		return new JFIFChunkInfo();
	}

	private static int readLength(InputStream chunkSection) throws IOException, MalformedJPEGException {
		return (JPEGUtil.read(chunkSection) << 8) + JPEGUtil.read(chunkSection);
	}

	private static String readIdentifier(InputStream chunkSection) throws IOException {
		byte[] identifierBytes = new byte[5];
		int bytesRead = chunkSection.read(identifierBytes);
		if (bytesRead != 5) throw new IOException("Unexpected end of stream");

		return new String(identifierBytes);
	}

}
