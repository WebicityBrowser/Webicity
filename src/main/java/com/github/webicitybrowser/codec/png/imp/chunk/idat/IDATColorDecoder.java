package com.github.webicitybrowser.codec.png.imp.chunk.idat;

public final class IDATColorDecoder {
	
	private IDATColorDecoder() {}

	public static byte[] decodeTrueColor(byte[] chunk, IDATContext idatContext) {
		byte bitDepth = idatContext.ihdrChunkInfo().bitDepth();
		int totalValues = determineTotalValues(chunk.length, bitDepth);
		byte[] decoded = new byte[totalValues / 3 * 4];
		int decodedIndex = 0;
		for (int i = 0; i < totalValues; i += 3) {
			decoded[decodedIndex++] = scaledByteOf(chunk, i, bitDepth);
			decoded[decodedIndex++] = chunk[i + 1];
			decoded[decodedIndex++] = chunk[i + 2];
			decoded[decodedIndex++] = (byte) 255;
		}

		return decoded;
	}

	public static byte[] decodeTrueColorWithAlpha(byte[] chunk, IDATContext idatContext) {
		byte bitDepth = idatContext.ihdrChunkInfo().bitDepth();
		int totalValues = determineTotalValues(chunk.length, bitDepth);
		byte[] decoded = new byte[totalValues];
		for (int i = 0; i < totalValues; i++) {
			decoded[i] = scaledByteOf(chunk, i, bitDepth);
		}

		return decoded;
	}

	private static int determineTotalValues(int length, byte bitDepth) {
		return length * 8 / bitDepth;
	}

	private static byte scaledByteOf(byte[] chunk, int i, byte bitDepth) {
		return (byte) (255f / bitDepth * byteOf(chunk, i, bitDepth));
	}

	private static byte byteOf(byte[] chunk, int i, byte bitDepth) {
		int byteIndex = i * bitDepth / 8;
		int bitIndex = i * bitDepth % 8;
		int byteMask = (1 << bitDepth) - 1;
		return (byte) ((chunk[byteIndex] >> (8 - bitIndex - bitDepth)) & byteMask);
	}

}
