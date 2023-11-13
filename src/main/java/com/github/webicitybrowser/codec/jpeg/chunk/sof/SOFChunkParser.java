package com.github.webicitybrowser.codec.jpeg.chunk.sof;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public final class SOFChunkParser {
	
	private SOFChunkParser() {}

	public static SOFChunkInfo read(InputStream chunkSection) throws IOException, MalformedJPEGException {
		int remainingLength = JPEGUtil.readTwoByte(chunkSection) - 2;
		if (remainingLength < 9 || (remainingLength - 9) % 3 != 0) {
			throw new MalformedJPEGException("SOF chunk length mismatch");
		}

		for (int i = 0; i < remainingLength; i++) {
			JPEGUtil.read(chunkSection);
		}

		return new SOFChunkInfo();
	}

}
