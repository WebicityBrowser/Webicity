package com.github.webicitybrowser.codec.jpeg;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser.DHTBinaryTree;
import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.jfif.JFIFChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkInfo.SOFComponentInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.sos.SOSChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sos.SOSChunkInfo.SOSComponentInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sos.SOSChunkParser;
import com.github.webicitybrowser.codec.jpeg.scan.EntropyDecoder;
import com.github.webicitybrowser.codec.jpeg.scan.ScanComponent;
import com.github.webicitybrowser.codec.jpeg.scan.ScanComponentResult;
import com.github.webicitybrowser.codec.jpeg.scan.ScanParser;
import com.github.webicitybrowser.codec.jpeg.scan.huffman.HuffmanEntropyDecoder;
import com.github.webicitybrowser.codec.jpeg.scan.primitivecollection.BitStream;
import com.github.webicitybrowser.codec.jpeg.stage.ZigZagDecoder;

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

		int finalByte = Assertions.assertDoesNotThrow(() -> chunkSection.read());
		Assertions.assertEquals(-1, finalByte);
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

		int finalByte = Assertions.assertDoesNotThrow(() -> chunkSection.read());
		Assertions.assertEquals(-1, finalByte);
	}

	@Test
	@DisplayName("Can parse DQT chunk with two byte size")
	public void canParseDQTChunkWithTwoByteSize() {
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

		int finalByte = Assertions.assertDoesNotThrow(() -> chunkSection.read());
		Assertions.assertEquals(-1, finalByte);
	}

	@Test
	@DisplayName("Can parse SOF chunk")
	public void canParseSOFChunk() {
		InputStream chunkSection = new ByteArrayInputStream(new byte[] {
			0, 11, 8, 0, 8, 0, 8, 1, 0, 34, 0
		});

		SOFChunkInfo chunkInfo = Assertions.assertDoesNotThrow(() -> SOFChunkParser.read(chunkSection));
		Assertions.assertEquals(8, chunkInfo.width());
		Assertions.assertEquals(8, chunkInfo.height());
		SOFComponentInfo[] components = chunkInfo.components();
		Assertions.assertEquals(0, components[0].componentId());
		Assertions.assertEquals(2, components[0].horizontalSamplingFactor());
		Assertions.assertEquals(2, components[0].verticalSamplingFactor());

		int finalByte = Assertions.assertDoesNotThrow(() -> chunkSection.read());
		Assertions.assertEquals(-1, finalByte);
	}

	@Test
	@DisplayName("Can parse simple DHT chunk")
	public void canParseSimpleDHTChunk() {
		InputStream chunkSection = new ByteArrayInputStream(new byte[] {
			0, 31, 0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
		});

		DHTChunkInfo dhtChunkInfo = Assertions.assertDoesNotThrow(() -> DHTChunkParser.read(chunkSection));
		DHTBinaryTree testValue = dhtChunkInfo.dcHuffmanTables()[0].left().right().left();
		Assertions.assertEquals(2, testValue.value());

		int finalByte = Assertions.assertDoesNotThrow(() -> chunkSection.read());
		Assertions.assertEquals(-1, finalByte);
	}

	@Test
	@DisplayName("Can parse SOS chunk")
	public void canParseSimpleSOSChunk() {
		InputStream chunkSection = new ByteArrayInputStream(new byte[] {
			0, 10, 2, 14, 18, 15, 18, 1, 1, 36
		});

		SOSChunkInfo chunkInfo = Assertions.assertDoesNotThrow(() -> SOSChunkParser.read(chunkSection));

		int finalByte = Assertions.assertDoesNotThrow(() -> chunkSection.read());
		Assertions.assertEquals(-1, finalByte);

		SOSComponentInfo[] components = chunkInfo.components();

		Assertions.assertEquals(14, components[0].componentId());
		Assertions.assertEquals(1, components[0].dcCodingTableSelector());
		Assertions.assertEquals(2, components[0].acCodingTableSelector());

		Assertions.assertEquals(15, components[1].componentId());
		Assertions.assertEquals(1, components[1].dcCodingTableSelector());
		Assertions.assertEquals(2, components[1].acCodingTableSelector());
	}

	@Test
	@DisplayName("Can parse empty scan")
	public void canParseEmptyScan() {
		PushbackInputStream chunkSection = new PushbackInputStream(new ByteArrayInputStream(new byte[] {
			(byte) 0xFF, (byte) 0xD9
		}), 2);

		EntropyDecoder entropyDecoder = Mockito.mock(EntropyDecoder.class);
		Mockito.when(entropyDecoder.readBlock(Mockito.any())).thenReturn(new int[0]);
		ScanComponent scanComponent = new ScanComponent(1, entropyDecoder, 0, 1, 1);
		ScanComponentResult[] results = Assertions.assertDoesNotThrow(() -> ScanParser.read(chunkSection, new ScanComponent[] { scanComponent }));
		int[] output = results[0].data();
		Assertions.assertEquals(0, output.length);

		Assertions.assertEquals(0xFF, Assertions.assertDoesNotThrow(() -> chunkSection.read()));
		Assertions.assertEquals(0xD9, Assertions.assertDoesNotThrow(() -> chunkSection.read()));
	}

	@Test
	@DisplayName("Can parse scan with data")
	public void canParseScanWithData() {
		PushbackInputStream chunkSection = new PushbackInputStream(new ByteArrayInputStream(new byte[] {
			1, (byte) 0xFF, (byte) 0xD9
		}), 2);

		EntropyDecoder entropyDecoder = Mockito.mock(EntropyDecoder.class);
		
		Mockito.doAnswer(invocation -> {
			BitStream bitStream = invocation.getArgument(0);
			for (int i = 0; i < 7; i++) {
				Assertions.assertEquals(0, bitStream.readBit());
			}
			Assertions.assertEquals(1, bitStream.readBit());
			return new int[] { 5, -1, 6 };
		}).when(entropyDecoder).readBlock(Mockito.any());

		ScanComponent scanComponent = new ScanComponent(1, entropyDecoder, 0, 1, 1);
		ScanComponentResult[] results = Assertions.assertDoesNotThrow(() -> ScanParser.read(chunkSection, new ScanComponent[] { scanComponent }));
		int[] output = results[0].data();

		Assertions.assertArrayEquals(new int[] { 5, -1, 6 }, output);

		Assertions.assertEquals(0xFF, Assertions.assertDoesNotThrow(() -> chunkSection.read()));
		Assertions.assertEquals(0xD9, Assertions.assertDoesNotThrow(() -> chunkSection.read()));
	}

	@Test
	@DisplayName("Can parse scan with stuffed data")
	public void canParseScanWithStuffedData() {
		PushbackInputStream chunkSection = new PushbackInputStream(new ByteArrayInputStream(new byte[] {
			(byte) 0xFF, 0x00, 0x00, (byte) 0xFF, (byte) 0xD9
		}), 2);

		EntropyDecoder entropyDecoder = Mockito.mock(EntropyDecoder.class);
		Mockito.doAnswer(invocation -> {
			BitStream bitStream = invocation.getArgument(0);
			for (int i = 0; i < 8; i++) {
				Assertions.assertEquals(1, bitStream.readBit());
			}
			while (bitStream.remaining() > 0) {
				Assertions.assertEquals(0, bitStream.readBit());
			}

			return new int[] { 5, -1, 6 };
		}).when(entropyDecoder).readBlock(Mockito.any());

		ScanComponent scanComponent = new ScanComponent(1, entropyDecoder, 0, 1, 1);
		Assertions.assertDoesNotThrow(() -> ScanParser.read(chunkSection, new ScanComponent[] { scanComponent }));

		Assertions.assertEquals(0xFF, Assertions.assertDoesNotThrow(() -> chunkSection.read()));
		Assertions.assertEquals(0xD9, Assertions.assertDoesNotThrow(() -> chunkSection.read()));
	}

	@Test
	@DisplayName("Can parse huffman encoded data with immediate EOB")
	public void canParseHuffmanEncodedDataWithImmediateEOB() {
		byte[] chunkSection = new byte[] {
			0b01000111
		};

		DHTBinaryTree dcBinaryTree = new DHTBinaryTree(-1,
			new DHTBinaryTree(-1, null, new DHTBinaryTree(2, null, null)), null);
		DHTBinaryTree acBinaryTree = new DHTBinaryTree(-1,
			new DHTBinaryTree(-1, null, new DHTBinaryTree(0, null, null)), null);

		EntropyDecoder entropyDecoder = new HuffmanEntropyDecoder(dcBinaryTree, acBinaryTree);
		BitStream bitStream = new BitStream(chunkSection);
		int[] output = entropyDecoder.readBlock(bitStream);

		Assertions.assertEquals(64, output.length);
		Assertions.assertEquals(-3, output[0]);
		for (int i = 1; i < 64; i++) {
			Assertions.assertEquals(0, output[i]);
		}
	}

	@Test
	@DisplayName("Can parse huffman encoded data with RLE AC values")
	public void canParseHuffmanEncodedDataWithRLEACValues() {
		byte[] chunkSection = new byte[] {
			0b01000100, 0b01000100, 0b00001111
		};

		DHTBinaryTree dcBinaryTree = new DHTBinaryTree(-1,
			new DHTBinaryTree(-1, null, new DHTBinaryTree(2, null, null)), null);
		DHTBinaryTree acBinaryTree = new DHTBinaryTree(-1, new DHTBinaryTree(-1,
			new DHTBinaryTree(0b11100010, null, null),
			new DHTBinaryTree(0b11110010, null, null)),
		null);

		EntropyDecoder entropyDecoder = new HuffmanEntropyDecoder(dcBinaryTree, acBinaryTree);
		BitStream bitStream = new BitStream(chunkSection);
		int[] output = entropyDecoder.readBlock(bitStream);

		Assertions.assertArrayEquals(new int[] {
			-3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			-3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			-3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			-3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -3
		}, output);
	}

	@Test
	@DisplayName("Can parse huffman encoded data with multiple blocks")
	public void canParseHuffmanEncodedDataWithMultipleBlocks() {
		byte[] chunkSection = new byte[] {
			0b01000101, 0b00011111
		};

		DHTBinaryTree dcBinaryTree = new DHTBinaryTree(-1,
			new DHTBinaryTree(-1, null, new DHTBinaryTree(2, null, null)), null);
		DHTBinaryTree acBinaryTree = new DHTBinaryTree(-1,
			new DHTBinaryTree(-1, null, new DHTBinaryTree(0, null, null)), null);

		EntropyDecoder entropyDecoder = new HuffmanEntropyDecoder(dcBinaryTree, acBinaryTree);
		BitStream bitStream = new BitStream(chunkSection);
		int[] output1 = entropyDecoder.readBlock(bitStream);
		int[] output2 = entropyDecoder.readBlock(bitStream);

		Assertions.assertEquals(64, output1.length);
		Assertions.assertEquals(64, output2.length);
		Assertions.assertEquals(-3, output1[0]);
		Assertions.assertEquals(-6, output2[0]);
		for (int i = 1; i < 64; i++) {
			Assertions.assertEquals(0, output1[i]);
			Assertions.assertEquals(0, output2[i]);
		}
	}

	@Test
	@DisplayName("Can un-zig-zag data")
	public void canUnZigZagData() {
		int[] input = new int[] {
			0, 1, 8, 16, 9, 2, 3, 10,
			17, 24, 32, 25, 18, 11, 4, 5,
			12, 19, 26, 33, 40, 48, 41, 34,
			27, 20, 13, 6, 7, 14, 21, 28,
			35, 42, 49, 56, 57, 50, 43, 36,
			29, 22, 15, 23, 30, 37, 44, 51,
			58, 59, 52, 45, 38, 31, 39, 46,
			53, 60, 61, 54, 47, 55, 62, 63
		};

		int[] output = ZigZagDecoder.decode(input, 0);
		Assertions.assertEquals(64, output.length);
		for (int i = 0; i < 64; i++) {
			Assertions.assertEquals(i, output[i]);
		}
	}

}
