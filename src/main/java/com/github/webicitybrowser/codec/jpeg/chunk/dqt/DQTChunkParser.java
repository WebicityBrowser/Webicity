package com.github.webicitybrowser.codec.jpeg.chunk.dqt;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public class DQTChunkParser {
	
	private DQTChunkParser() {}

	public static DQTChunkInfo read(InputStream chunkSection) throws IOException, MalformedJPEGException {
		int remainingLength = JPEGUtil.readTwoByte(chunkSection) - 2;
		int[][] tables = new int[4][];
		while (remainingLength > 0) {
			remainingLength -= readTable(chunkSection, tables);
		}
		if (remainingLength < 0) throw new MalformedJPEGException("DQT chunk length mismatch");

		return new DQTChunkInfo(tables);
	}

	private static int readTable(InputStream chunkSection, int[][] tables) throws IOException, MalformedJPEGException {
		int length = 1;

		int tableInfo = chunkSection.read();
		int tableId = tableInfo & 0x0F;
		if (tableId < 0 || tableId > 3) throw new MalformedJPEGException("Invalid DQT table id");
		int byteSize = (tableInfo >>> 4) + 1;
		if (byteSize != 1 && byteSize != 2) throw new MalformedJPEGException("Invalid DQT table byte size");
		int[] table = new int[64];
		tables[tableId] = table;

		for (int i = 0; i < 64; i++) {
			table[i] = readDQTValue(chunkSection, byteSize);
			length += byteSize;
		}

		return length;
	}

	private static int readDQTValue(InputStream chunkSection, int byteSize) throws IOException, MalformedJPEGException {
		return byteSize == 1 ? JPEGUtil.read(chunkSection) : JPEGUtil.readTwoByte(chunkSection);
	}

}
