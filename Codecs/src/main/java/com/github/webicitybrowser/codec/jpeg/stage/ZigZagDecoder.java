package com.github.webicitybrowser.codec.jpeg.stage;

public final class ZigZagDecoder {

	private ZigZagDecoder() {}

	private static final int[] ZIGZAG = new int[] {
		0, 1, 5, 6, 14, 15, 27, 28,
		2, 4, 7, 13, 16, 26, 29, 42,
		3, 8, 12, 17, 25, 30, 41, 43,
		9, 11, 18, 24, 31, 40, 44, 53,
		10, 19, 23, 32, 39, 45, 52, 54,
		20, 22, 33, 38, 46, 51, 55, 60,
		21, 34, 37, 47, 50, 56, 59, 61,
		35, 36, 48, 49, 57, 58, 62, 63
	};

	public static int[] decode(int[] input, int offset) {
		assert input.length >= offset + 64;
		int[] output = new int[64];
		for (int i = 0; i < 64; i++) {
			output[i] = input[ZIGZAG[i] + offset];
		}

		return output;
	}

}
