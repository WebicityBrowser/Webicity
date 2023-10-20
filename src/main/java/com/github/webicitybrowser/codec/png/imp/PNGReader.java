package com.github.webicitybrowser.codec.png.imp;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.codec.png.PNGResult;
import com.github.webicitybrowser.codec.png.exception.InvalidPNGSignatureException;
import com.github.webicitybrowser.codec.png.exception.MalformedPNGException;

public class PNGReader {
	
	private static final byte[] SIGNATURE = asBytes(new int[] { 137, 'P', 'N', 'G', 13, 10, 26, 10 });

	public PNGResult read(InputStream dataStream) throws MalformedPNGException, IOException {
		checkSignature(dataStream);

		return null;
	}

	private void checkSignature(InputStream dataStream) throws MalformedPNGException, IOException {
		for (int i = 0; i < SIGNATURE.length; i++) {
			if (dataStream.read() != SIGNATURE[i]) {
				throw new InvalidPNGSignatureException();
			}
		}
	}

	private static byte[] asBytes(int[] data) {
		byte[] bytes = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			bytes[i] = (byte) data[i];
		}
		return bytes;
	}

}
