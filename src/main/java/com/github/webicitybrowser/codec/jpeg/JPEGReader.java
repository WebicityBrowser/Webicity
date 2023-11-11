package com.github.webicitybrowser.codec.jpeg;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.chunk.dht.DHTChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.dqt.DQTChunkParser;
import com.github.webicitybrowser.codec.jpeg.chunk.sof.SOFChunkParser;
import com.github.webicitybrowser.codec.jpeg.exception.InvalidJPEGSignatureException;
import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;
import com.github.webicitybrowser.codec.jpeg.util.JPEGUtil;

public class JPEGReader {

	public JPEGResult read(InputStream dataStream) throws IOException, MalformedJPEGException {
		clearState();
		checkSignature(dataStream);
		while (true) {
			int chunkType = JPEGUtil.readTwoByte(dataStream);
			handleNextChunk(chunkType, dataStream);
		}
		//throw new UnsupportedOperationException("Unimplemented method 'read'");
	}

	private void handleNextChunk(int chunkType, InputStream dataStream) throws IOException, MalformedJPEGException {
		switch (chunkType) {
		case 0xFFDB: // Data Quantization Table
			DQTChunkParser.read(dataStream).tables();
			break;
		case 0xFFC2: // Progressive DCT
			SOFChunkParser.read(dataStream);
			break;
		case 0xFFC4: // Huffman Table
			DHTChunkParser.read(dataStream);
			break;
		default:
			throw new UnsupportedOperationException("Unimplemented chunk type: " + Integer.toHexString(chunkType));
		}
	}

	private void clearState() {
		
	}

	private void checkSignature(InputStream dataStream) throws InvalidJPEGSignatureException, IOException {
		if (dataStream.read() != 0xFF || dataStream.read() != 0xD8) {
			throw new InvalidJPEGSignatureException();
		}
	}

}
