package com.github.webicitybrowser.codec.jpeg;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.jfif.JFIFChunkParser;

public class JPEGReaderTest {
	
	@Test
	@DisplayName("Can parse JFIF chunk")
	public void canParseJFIFChunk() {
		InputStream chunkSection = new ByteArrayInputStream(new byte[] { 0, 16, 'J', 'F', 'I', 'F', 0, 1, 2, 0, 0, 0, 0, 0, 0, 0 });
		Assertions.assertDoesNotThrow(() -> JFIFChunkParser.read(chunkSection));
		int finalByte = Assertions.assertDoesNotThrow(() -> chunkSection.read());
		Assertions.assertEquals(-1, finalByte);
	}

	@Test
	@DisplayName("Can parse simple DQT chunk")
	public void canParseDQTChunk() {
		InputStream chunkSection = new ByteArrayInputStream(new byte[] {
			0, 67, 0,
			0, 1, 2, 3, 4, 5, 6, 7,
			8, 9, 10, 11, 12, 13, 14, 15,
			16, 17, 18, 19, 20, 21, 22, 23,
			24, 25, 26, 27, 28, 29, 30, 31,
			32, 33, 34, 35, 36, 37, 38, 39,
			40, 41, 42, 43, 44, 45, 46, 47,
			48, 49, 50, 51, 52, 53, 54, 55,
			56, 57, 58, 59, 60, 61, 62, 63
		});

		DQTChunkInfo chunk = Assertions.assertDoesNotThrow(() -> DQTChunkParser.read(chunkSection));
		int[] table = chunk.tables()[0];
		Assertions.assertEquals(11, table[11]);
	}

	@Test
	@DisplayName("Can parse DQT chunk with two tables")
	public void canParseDQTChunkWithTwoTables() {
		InputStream chunkSection = new ByteArrayInputStream(new byte[] {
			0, (byte) 132,
			0,
			0, 1, 2, 3, 4, 5, 6, 7,
			8, 9, 10, 11, 12, 13, 14, 15,
			16, 17, 18, 19, 20, 21, 22, 23,
			24, 25, 26, 27, 28, 29, 30, 31,
			32, 33, 34, 35, 36, 37, 38, 39,
			40, 41, 42, 43, 44, 45, 46, 47,
			48, 49, 50, 51, 52, 53, 54, 55,
			56, 57, 58, 59, 60, 61, 62, 63,
			3,
			64, 64, 64, 64, 64, 64, 64, 64,
			64, 64, 64, 64, 64, 64, 64, 64,
			64, 64, 64, 64, 64, 64, 64, 64,
			64, 64, 64, 64, 64, 64, 64, 64,
			64, 64, 64, 64, 64, 64, 64, 64,
			64, 64, 64, 64, 64, 64, 64, 64,
			64, 64, 64, 64, 64, 64, 64, 64,
			64, 64, 64, 64, 64, 64, 64, 64
		});

		DQTChunkInfo chunk = Assertions.assertDoesNotThrow(() -> DQTChunkParser.read(chunkSection));
		int[] table = chunk.tables()[0];
		Assertions.assertEquals(11, table[11]);
		table = chunk.tables()[3];
		Assertions.assertEquals(64, table[11]);
	}

	@Test
	@DisplayName("Can parse DQT chunk with two byte size")
	public void canParseDQTChunkWithTwoByteSize() {
		System.out.println("DQT chunk with two byte size");
		InputStream chunkSection = new ByteArrayInputStream(new byte[] {
			0, (byte) 131, (byte) 16,
			1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1, 6, 1, 7,
			1, 8, 1, 9, 1, 10, 1, 11, 1, 12, 1, 13, 1, 14, 1, 15,
			1, 16, 1, 17, 1, 18, 1, 19, 1, 20, 1, 21, 1, 22, 1, 23,
			1, 24, 1, 25, 1, 26, 1, 27, 1, 28, 1, 29, 1, 30, 1, 31,
			1, 32, 1, 33, 1, 34, 1, 35, 1, 36, 1, 37, 1, 38, 1, 39,
			1, 40, 1, 41, 1, 42, 1, 43, 1, 44, 1, 45, 1, 46, 1, 47,
			1, 48, 1, 49, 1, 50, 1, 51, 1, 52, 1, 53, 1, 54, 1, 55,
			1, 56, 1, 57, 1, 58, 1, 59, 1, 60, 1, 61, 1, 62, 1, 63
		});

		DQTChunkInfo chunk = Assertions.assertDoesNotThrow(() -> DQTChunkParser.read(chunkSection));
		int[] table = chunk.tables()[0];
		Assertions.assertEquals(267, table[11]);
	}

}
