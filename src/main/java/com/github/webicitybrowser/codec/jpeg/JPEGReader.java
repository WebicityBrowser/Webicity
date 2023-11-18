package com.github.webicitybrowser.codec.jpeg;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser.DHTBinaryTree;
import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.sos.SOSChunkParser;
import com.github.webicitybrowser.codec.jpeg.exception.InvalidJPEGSignatureException;
import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.scan.EntropyDecoder;
import com.github.webicitybrowser.codec.jpeg.scan.ScanParser;
import com.github.webicitybrowser.codec.jpeg.scan.huffman.HuffmanEntropyDecoder;
import com.github.webicitybrowser.codec.jpeg.stage.DCTDecoder;
import com.github.webicitybrowser.codec.jpeg.stage.ZigZagDecoder;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public class JPEGReader {

	// TODO: Need a good refactoring when done - many concerns are mixed together
	private DHTBinaryTree[] dcHuffmanTables = new DHTBinaryTree[4];
	private DHTBinaryTree[] acHuffmanTables = new DHTBinaryTree[4];
	private DQTChunkInfo dqtChunkInfo;
	private SOFChunkInfo sofChunkInfo;
	private byte[] completedImage;

	public JPEGResult read(InputStream rawDataStream) throws IOException, MalformedJPEGException {
		PushbackInputStream dataStream = new PushbackInputStream(rawDataStream, 2);
		clearState();
		checkSignature(dataStream);

		while (!handleNextChunk(JPEGUtil.readTwoByte(dataStream), dataStream));
		return new JPEGResult(sofChunkInfo.width(), sofChunkInfo.height(), completedImage);
	}

	private boolean handleNextChunk(int chunkType, PushbackInputStream dataStream) throws IOException, MalformedJPEGException {
		switch (chunkType) {
		case 0xFFDB: // Data Quantization Table
			dqtChunkInfo = DQTChunkParser.read(dataStream);
			break;
		case 0xFFC0: // Baseline DCT
		case 0xFFC2: // Progressive DCT
			sofChunkInfo = SOFChunkParser.read(dataStream);
			this.completedImage = new byte[sofChunkInfo.width() * sofChunkInfo.height() * 4];
			break;
		case 0xFFC4: // Huffman Table
			readHuffmanTables(dataStream);
			break;
		case 0xFFDA: // Start of Scan
			int[] scanData = startScan(dataStream);
			decodeData(scanData);
			break;
		case 0xFFDD: // Restart Interval
			ignoreChunk(dataStream);
			break;
		case 0xFFD9: // End of Image: WE MADE IT!
			return true;
		default:
			if (chunkType >= 0xFFE0 && chunkType <= 0xFFFF) {
				// Application or extension specific
				ignoreChunk(dataStream);
				break;
			}
			throw new UnsupportedOperationException("Unimplemented chunk type: " + Integer.toHexString(chunkType));
		}

		return false;
	}

	private void clearState() {
		dcHuffmanTables = new DHTBinaryTree[4];
		acHuffmanTables = new DHTBinaryTree[4];
		dqtChunkInfo = null;
		sofChunkInfo = null;
		completedImage = null;
	}

	private void checkSignature(InputStream dataStream) throws InvalidJPEGSignatureException, IOException {
		if (dataStream.read() != 0xFF || dataStream.read() != 0xD8) {
			throw new InvalidJPEGSignatureException();
		}
	}

	private void ignoreChunk(InputStream dataStream) throws IOException, MalformedJPEGException {
		int chunkLength = JPEGUtil.readTwoByte(dataStream) - 2;
		if (chunkLength < 0) {
			throw new MalformedJPEGException("Extra chunk length mismatch");
		}
		for (int i = 0; i < chunkLength; i++) {
			JPEGUtil.read(dataStream);
		}
	}

	private void readHuffmanTables(PushbackInputStream dataStream) throws IOException, MalformedJPEGException {
		DHTChunkInfo chunkInfo = DHTChunkParser.read(dataStream);
		DHTBinaryTree[] dcHuffmanTables = chunkInfo.dcHuffmanTables();
		DHTBinaryTree[] acHuffmanTables = chunkInfo.acHuffmanTables();
		for (int i = 0; i < 4; i++) {
			if (dcHuffmanTables[i] != null) {
				this.dcHuffmanTables[i] = dcHuffmanTables[i];
			}
			if (acHuffmanTables[i] != null) {
				this.acHuffmanTables[i] = acHuffmanTables[i];
			}
		}
	}

	private int[] startScan(PushbackInputStream dataStream) throws IOException, MalformedJPEGException {
		SOSChunkParser.read(dataStream);
		// TODO: We don't know if we are even using huffman, much less which table to use
		DHTBinaryTree dcHuffmanTable = dcHuffmanTables[0];
		DHTBinaryTree acHuffmanTable = acHuffmanTables[0];
		EntropyDecoder entropyDecoder = new HuffmanEntropyDecoder(dcHuffmanTable, acHuffmanTable);
		return ScanParser.read(dataStream, entropyDecoder);
	}

	private void decodeData(int[] scanData) {
		for (int x = 0; x < sofChunkInfo.width(); x += 8) {
			for (int y = 0; y < sofChunkInfo.height(); y += 8) {
				decodeDataBlock(scanData, x, y);
			}
		}
	}

	private void decodeDataBlock(int[] scanData, int x, int y) {
		int[] quantizationTable = ZigZagDecoder.decode(dqtChunkInfo.tables()[0], 0);
		int blockOffset = x * 8 + y * sofChunkInfo.width();
		int[] cells = ZigZagDecoder.decode(scanData, blockOffset);
		int[] decoded = DCTDecoder.decodeDCT(cells, quantizationTable);
		for (int i = 0; i < 64; i++) {
			int cell = decoded[i];
			int cellX = i % 8;
			int cellY = i / 8;
			int pixelOffset = (x + cellX) * 4 + (y + cellY) * sofChunkInfo.width() * 4;
			
			cell += 128;
			cell = Math.max(0, Math.min(255, cell));
			completedImage[pixelOffset] = (byte) cell;
			completedImage[pixelOffset + 1] = (byte) cell;
			completedImage[pixelOffset + 2] = (byte) cell;
			completedImage[pixelOffset + 3] = (byte) 255;
		}
	}

}
