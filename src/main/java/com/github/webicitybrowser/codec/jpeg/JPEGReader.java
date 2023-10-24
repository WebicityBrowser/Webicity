package com.github.webicitybrowser.codec.jpeg;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.exception.InvalidJPEGSignatureException;

public class JPEGReader {
	
	public JPEGResult read(InputStream dataStream) throws InvalidJPEGSignatureException, IOException {
		clearState();
		checkSignature(dataStream);
		throw new UnsupportedOperationException("Unimplemented method 'read'");
	}

	private void clearState() {

	}

	private void checkSignature(InputStream dataStream) throws InvalidJPEGSignatureException, IOException {
		if (dataStream.read() != 0xFF || dataStream.read() != 0xD8) {
			throw new InvalidJPEGSignatureException();
		}
	}

}
