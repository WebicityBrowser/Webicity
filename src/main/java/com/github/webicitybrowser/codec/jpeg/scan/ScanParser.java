package com.github.webicitybrowser.codec.jpeg.scan;

import java.io.IOException;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public final class ScanParser {
	
	private ScanParser() {}

	public static int[] read(PushbackInputStream inputStream, EntropyDecoder entropyDecoder) throws IOException, MalformedJPEGException {
		while (true) {
			int nextByte = JPEGUtil.read(inputStream);
			int byteAfter = nextByte == 0xFF ? JPEGUtil.read(inputStream) : 0;
			if (byteAfter >= 0xD0 && byteAfter <= 0xD7) {
				entropyDecoder.restart();
				continue;
			} else if (byteAfter != 0x00) {
				inputStream.unread(byteAfter);
				inputStream.unread(0xFF);
				break;
			}

			entropyDecoder.next((byte) nextByte);
		}
		entropyDecoder.done();
		
		return entropyDecoder.getDecoded();
	}

}
