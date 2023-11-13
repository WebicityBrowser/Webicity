package com.github.webicitybrowser.codec.jpeg.chunk.sos;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public final class SOSChunkParser {
	
	private SOSChunkParser() {}

	public static SOSChunkInfo read(InputStream chunkSection) throws IOException, MalformedJPEGException {
		int length = JPEGUtil.readTwoByte(chunkSection) - 2;
		if (length < 6) throw new MalformedJPEGException("SOS chunk length mismatch");
		for (int i = 0; i < length; i++) {
			JPEGUtil.read(chunkSection);
		}

		return new SOSChunkInfo();
	}

}
