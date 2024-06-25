package com.github.webicitybrowser.codec.png.chunk.idat;

import com.github.webicitybrowser.codec.png.chunk.plte.PLTEChunkInfo;

public final class IDATColorDecoder {
	
	private IDATColorDecoder() {}

	public static byte[] decodeTrueColor(byte[] chunk, IDATContext idatContext) {
		byte bitDepth = idatContext.ihdrChunkInfo().bitDepth();
		int totalValues = determineTotalValues(chunk.length, bitDepth);
		byte[] decoded = new byte[totalValues / 3 * 4];
		int decodedIndex = 0;
		for (int i = 0; i < totalValues; i += 3) {
			decoded[decodedIndex++] = scaledByteOf(chunk, i, bitDepth);
			decoded[decodedIndex++] = scaledByteOf(chunk, i + 1, bitDepth);
			decoded[decodedIndex++] = scaledByteOf(chunk, i + 2, bitDepth);
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

	public static byte[] decodeIndexedColor(byte[] data, IDATContext idatContext) {
		PLTEChunkInfo plteChunkInfo = idatContext.plteChunkInfo();
		byte[][] palette = plteChunkInfo.palette();

		byte bitDepth = idatContext.ihdrChunkInfo().bitDepth();
		int totalValues = determineTotalValues(data.length, bitDepth);
		byte[] decoded = new byte[totalValues * 4];
		int decodedIndex = 0;
		for (int i = 0; i < totalValues; i++) {
			int paletteIndex = byteOf(data, i, bitDepth) & 0xFF;
			decoded[decodedIndex++] = palette[paletteIndex][0];
			decoded[decodedIndex++] = palette[paletteIndex][1];
			decoded[decodedIndex++] = palette[paletteIndex][2];
			decoded[decodedIndex++] = (byte) 255;
		}

		return decoded;
	}

	private static int determineTotalValues(int length, byte bitDepth) {
		return length * 8 / bitDepth;
	}

	private static byte scaledByteOf(byte[] chunk, int i, byte bitDepth) {
		int scaling = 255 / ((1 << bitDepth) - 1);
		return (byte) (byteOf(chunk, i, bitDepth) * scaling);
	}

	private static byte byteOf(byte[] chunk, int i, byte bitDepth) {
		int byteIndex = i * bitDepth / 8;
		int bitIndex = i * bitDepth % 8;
		int byteMask = (1 << bitDepth) - 1;
		return (byte) ((chunk[byteIndex] >>> (8 - bitIndex - bitDepth)) & byteMask);
	}

}
