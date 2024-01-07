package com.github.webicitybrowser.codec.jpeg;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser.DHTBinaryTree;
import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.jfif.JFIFChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkInfo;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkParser;
import com.github.webicitybrowser.codec.jpeg.exception.InvalidJPEGSignatureException;
import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.stage.ScanDecoder;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public class JPEGReader {

	public JPEGResult read(InputStream rawDataStream) throws IOException, MalformedJPEGException {
		PushbackInputStream dataStream = new PushbackInputStream(rawDataStream, 2);
		checkSignature(dataStream);
		JPEGState jpegState = new JPEGState(dataStream); 

		while (!handleNextChunk(JPEGUtil.readTwoByte(dataStream), jpegState));
		return createResult(jpegState);
	}

	private boolean handleNextChunk(int chunkType, JPEGState jpegState) throws IOException, MalformedJPEGException {
		PushbackInputStream dataStream = jpegState.dataStream();
		switch (chunkType) {
		case 0xFFE0:
			JFIFChunkParser.read(dataStream);
			break;
		case 0xFFDB: // Data Quantization Table
			readQuantizationTables(jpegState);
			break;
		case 0xFFC0: // Baseline DCT
		//case 0xFFC2: // Progressive DCT
			jpegState.setSOFChunkInfo(SOFChunkParser.read(dataStream));
			break;
		case 0xFFC4: // Huffman Table
			readHuffmanTables(jpegState);
			break;
		case 0xFFDA: // Start of Scan
			jpegState.setCompletedImage(ScanDecoder.decodeScan(jpegState));
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

	private JPEGResult createResult(JPEGState jpegState) {
		SOFChunkInfo sofChunkInfo = jpegState.sofChunkInfo();
		byte[] completedImage = jpegState.completedImage();
		return new JPEGResult(sofChunkInfo.width(), sofChunkInfo.height(), completedImage);
	}

	private void readQuantizationTables(JPEGState jpegState) throws IOException, MalformedJPEGException {
		PushbackInputStream dataStream = jpegState.dataStream();
		DQTChunkInfo chunkInfo = DQTChunkParser.read(dataStream);
		int[][] tables = chunkInfo.tables();
		for (int i = 0; i < 4; i++) {
			if (tables[i] != null) {
				jpegState.installQuantizationTable(i, tables[i]);
			}
		}
	}

	private void readHuffmanTables(JPEGState jpegState) throws IOException, MalformedJPEGException {
		PushbackInputStream dataStream = jpegState.dataStream();
		DHTChunkInfo chunkInfo = DHTChunkParser.read(dataStream);
		DHTBinaryTree[] dcHuffmanTables = chunkInfo.dcHuffmanTables();
		DHTBinaryTree[] acHuffmanTables = chunkInfo.acHuffmanTables();
		for (int i = 0; i < 4; i++) {
			if (dcHuffmanTables[i] != null) {
				jpegState.installDCHuffmanTable(i, dcHuffmanTables[i]);
			}
			if (acHuffmanTables[i] != null) {
				jpegState.installACHuffmanTable(i, acHuffmanTables[i]);
			}
		}
	}

}
