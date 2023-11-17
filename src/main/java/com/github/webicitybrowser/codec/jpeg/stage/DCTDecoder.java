package com.github.webicitybrowser.codec.jpeg.stage;

public class DCTDecoder {
	
	private DCTDecoder() {}

	public static int[] decodeDCT(int[] cells, int[] quantizationTable) {
		int[] dequantized = dequantize(cells, quantizationTable);

		int[] decoded = new int[64];
		for (int i = 0; i < 64; i++) {
			decoded[i] = idct(dequantized, i);
		}

		return decoded;
	}

	private static int[] dequantize(int[] cells, int[] quantizationTable) {
		int[] dequantized = new int[64];
		for (int i = 0; i < 64; i++) {
			dequantized[i] = cells[i] * quantizationTable[i];
		}

		return dequantized;
	}

	private static int idct(int[] dequantized, int i) {
		double result = 0;

		int x = i % 8;
		int y = i / 8;

		for (int j = 0; j < 64; j++) {
			double c = (j == 0) ? (1 / Math.sqrt(2)) : 1;
			int u = j % 8;
			int v = j / 8;
			result += c * dequantized[j] * Math.cos((2 * x + 1) * u * Math.PI / 16) * Math.cos((2 * y + 1) * v * Math.PI / 16);
		}

		return (int) (result / 4);
	}

}
