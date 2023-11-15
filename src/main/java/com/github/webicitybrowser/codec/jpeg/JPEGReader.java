package com.github.webicitybrowser.codec.jpeg;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser.DHTBinaryTree;
import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.sos.SOSChunkParser;
import com.github.webicitybrowser.codec.jpeg.exception.InvalidJPEGSignatureException;
import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.scan.EntropyDecoder;
import com.github.webicitybrowser.codec.jpeg.scan.ScanParser;
import com.github.webicitybrowser.codec.jpeg.scan.huffman.HuffmanEntropyDecoder;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public class JPEGReader {

	private DHTBinaryTree[] dcHuffmanTables = new DHTBinaryTree[4];
	private DHTBinaryTree[] acHuffmanTables = new DHTBinaryTree[4];

	public JPEGResult read(InputStream rawDataStream) throws IOException, MalformedJPEGException {
		PushbackInputStream dataStream = new PushbackInputStream(rawDataStream, 2);
		clearState();
		checkSignature(dataStream);

		while (!handleNextChunk(JPEGUtil.readTwoByte(dataStream), dataStream));
		return new JPEGResult(0, 0, new byte[0]);
	}

	private boolean handleNextChunk(int chunkType, PushbackInputStream dataStream) throws IOException, MalformedJPEGException {
		switch (chunkType) {
		case 0xFFDB: // Data Quantization Table
			DQTChunkParser.read(dataStream).tables();
			break;
		case 0xFFC0: // Baseline DCT
		case 0xFFC2: // Progressive DCT
			SOFChunkParser.read(dataStream);
			break;
		case 0xFFC4: // Huffman Table
			readHuffmanTables(dataStream);
			break;
		case 0xFFDA: // Start of Scan
			startScan(dataStream);
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

	private void startScan(PushbackInputStream dataStream) throws IOException, MalformedJPEGException {
		SOSChunkParser.read(dataStream);
		// TODO: We don't know if we are even using huffman, much less which table to use
		DHTBinaryTree dcHuffmanTable = dcHuffmanTables[0];
		DHTBinaryTree acHuffmanTable = acHuffmanTables[0];
		EntropyDecoder entropyDecoder = new HuffmanEntropyDecoder(dcHuffmanTable, acHuffmanTable);
		ScanParser.read(dataStream, entropyDecoder);
	}

}
