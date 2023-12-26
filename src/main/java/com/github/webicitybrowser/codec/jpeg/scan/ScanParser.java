package com.github.webicitybrowser.codec.jpeg.scan;

import java.io.IOException;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.scan.primitivecollection.BitStream;
import com.github.webicitybrowser.codec.jpeg.scan.primitivecollection.ByteArray;
import com.github.webicitybrowser.codec.jpeg.scan.primitivecollection.IntArray;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public final class ScanParser {
	
	private ScanParser() {}

	public static int[] read(PushbackInputStream inputStream, EntropyDecoder entropyDecoder) throws IOException, MalformedJPEGException {
		ByteArray inputBytes = new ByteArray();
		IntArray outputInts = new IntArray();

		while (true) {
			int nextByte = JPEGUtil.read(inputStream);
			int byteAfter = nextByte == 0xFF ? JPEGUtil.read(inputStream) : 0;
			if (byteAfter >= 0xD0 && byteAfter <= 0xD7) {
				addBlocks(inputBytes, outputInts, entropyDecoder);
				entropyDecoder.restart();
				continue;
			} else if (byteAfter != 0x00) {
				inputStream.unread(byteAfter);
				inputStream.unread(0xFF);
				break;
			}

			inputBytes.add((byte) nextByte);
		}

		addBlocks(inputBytes, outputInts, entropyDecoder);

		return outputInts.toArray();
	}

	private static void addBlocks(ByteArray inputBytes, IntArray outputInts, EntropyDecoder entropyDecoder) {
		BitStream bitStream = new BitStream(inputBytes.toArray());
		inputBytes.clear();
		while (!onlyOnes(bitStream)) {
			outputInts.add(entropyDecoder.readBlock(bitStream));
		}
	}

	private static boolean onlyOnes(BitStream bitStream) {
		for (int i = 0; i < bitStream.remaining(); i++) {
			if (bitStream.peek(i) != 1) return false;
		}

		return true;
	}

}
