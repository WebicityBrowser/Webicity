package com.github.webicitybrowser.codec.png;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.codec.png.chunk.ihdr.IHDRChunkInfo;
import com.github.webicitybrowser.codec.png.chunk.ihdr.IHDRChunkParser;
import com.github.webicitybrowser.codec.png.chunk.plte.PLTEChunkInfo;
import com.github.webicitybrowser.codec.png.chunk.plte.PLTEChunkParser;
import com.github.webicitybrowser.codec.png.exception.MalformedPNGException;

public class PNGReaderTest {
	
	private PNGReader pngReader;

	@BeforeEach
	public void setup() {
		pngReader = new PNGReader();
	}

	@Test
	@DisplayName("Can not parse bad PNG header")
	public void canNotParseBadHeaderPNG() {
		InputStream badHeaderStream = new ByteArrayInputStream(new byte[] { 2 });
		Assertions.assertThrows(MalformedPNGException.class, () -> pngReader.read(badHeaderStream));
	}

	@Test
	@DisplayName("PNG chunk info parser can gather chunk information")
	public void pngChunkInfoParserCanGatherChunkInformation() {
		// TODO: Set a proper CRC
		InputStream chunkSection = new ByteArrayInputStream(new byte[] { 0, 0, 0, 4, 'I', 'D', 'A', 'T', 1, 2, 3, 4, 0, 0, 0, 0 });
		PNGChunkInfo chunkInfo = Assertions.assertDoesNotThrow(() -> PNGChunkInfoReader.read(chunkSection));
		Assertions.assertArrayEquals(new byte[] { 'I', 'D', 'A', 'T' }, chunkInfo.type());
		Assertions.assertArrayEquals(new byte[] { 1, 2, 3, 4 }, chunkInfo.data());
	}

	// TODO: Add a test for CRC validation
	@Test
	@DisplayName("Can parse IHDR chunk")
	public void canParseIHDRChunk() {
		byte[] ihdrChunkData = new byte[] { 0, 0, 0, 4, 0, 0, 0, 8, 16, 6, 0, 0, 1 };
		IHDRChunkInfo ihdrChunkInfo = Assertions.assertDoesNotThrow(() -> IHDRChunkParser.parse(ihdrChunkData));
		Assertions.assertEquals(4, ihdrChunkInfo.width());
		Assertions.assertEquals(8, ihdrChunkInfo.height());
		Assertions.assertEquals(16, ihdrChunkInfo.bitDepth());
		Assertions.assertEquals(6, ihdrChunkInfo.colorType());
		Assertions.assertEquals(0, ihdrChunkInfo.compressionMethod());
		Assertions.assertEquals(0, ihdrChunkInfo.filterMethod());
		Assertions.assertEquals(1, ihdrChunkInfo.interlaceMethod());
	}

	@Test
	@DisplayName("Can parse PLTE chunk")
	public void canParsePLTEChunck() {
		byte[] plteChunkData = new byte[] { 5, 10, 15, 15, 10, 5 };
		PLTEChunkInfo plteChunkInfo = Assertions.assertDoesNotThrow(() -> PLTEChunkParser.parse(plteChunkData));
		Assertions.assertArrayEquals(
			new byte[][] {
				new byte[] { 5, 10, 15 },
				new byte[] { 15, 10, 5 }
			}, plteChunkInfo.palette()
		);
	}
	
	@Test
	@DisplayName("Can decode adam7 interlaced data horizontal pass")
	public void canDecodeAdam7InterlacedDataHorizontalPass() {
		
	}

}
