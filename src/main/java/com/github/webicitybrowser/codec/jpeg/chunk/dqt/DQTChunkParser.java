package com.github.webicitybrowser.codec.jpeg.chunk.dqt;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;

public class DQTChunkParser {
	
	private DQTChunkParser() {}

	public static DQTChunkInfo read(InputStream chunkSection) throws IOException, MalformedJPEGException {
		int remainingLength = readTwoByte(chunkSection) - 2;
		int[][] tables = new int[4][64];
		while (remainingLength > 0) {
			System.out.println("DQT chunk remaining length: " + remainingLength);
			remainingLength -= readTable(chunkSection, tables);
		}
		System.out.println("DQT chunk remaining length: " + remainingLength);
		if (remainingLength < 0) throw new MalformedJPEGException("DQT chunk length mismatch");

		return new DQTChunkInfo(tables);
	}

	private static int readTable(InputStream chunkSection, int[][] tables) throws IOException, MalformedJPEGException {
		int length = 1;

		int tableInfo = chunkSection.read();
		int tableId = tableInfo & 0x0F;
		if (tableId < 0 || tableId > 3) throw new MalformedJPEGException("Invalid DQT table id");
		int byteSize = (tableInfo >> 4) + 1;
		if (byteSize != 1 && byteSize != 2) throw new MalformedJPEGException("Invalid DQT table byte size");
		int[] table = tables[tableId];

		for (int i = 0; i < 64; i++) {
			table[i] = readDQTValue(chunkSection, byteSize);
			length += byteSize;
		}

		return length;
	}

	private static int readDQTValue(InputStream chunkSection, int byteSize) throws IOException, MalformedJPEGException {
		int dqtValue = byteSize == 1 ?
			chunkSection.read() & 0xFF :
			readTwoByte(chunkSection);
		if (dqtValue == -1) throw new MalformedJPEGException("Unexpected end of stream");

		return dqtValue;
	}

	private static int readTwoByte(InputStream chunkSection) throws IOException, MalformedJPEGException {
		int byteRead = chunkSection.read() & 0xFF;
		if (byteRead == -1) throw new IOException("Unexpected end of stream");
		int length = byteRead << 8;
		byteRead = chunkSection.read() & 0xFF;
		if (byteRead == -1) throw new IOException("Unexpected end of stream");
		length += byteRead;

		return length;
	}

}
