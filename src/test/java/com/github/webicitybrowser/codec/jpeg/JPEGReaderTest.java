package com.github.webicitybrowser.codec.jpeg;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.codec.jpeg.exception.chunk.jfif.JFIFChunkParser;

public class JPEGReaderTest {
	
	@Test
	@DisplayName("Can parse JFIF chunk")
	public void canParseJFIFChunk() {
			InputStream chunkSection = new ByteArrayInputStream(new byte[] { 0, 16, 'J', 'F', 'I', 'F', 0, 1, 2, 0, 0, 0, 0, 0, 0, 0 });
			Assertions.assertDoesNotThrow(() -> JFIFChunkParser.read(chunkSection));
			int finalByte = Assertions.assertDoesNotThrow(() -> chunkSection.read());
			Assertions.assertEquals(-1, finalByte);
	}

}
