package com.github.webicitybrowser.codec.jpeg.stage;

public class DCTDecoder {

	private static double START_MUL = 1 / Math.sqrt(2);
	private static double[][] IDCT_CACHE = new double[8][8];
	
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
			int u = j % 8;
			int v = j / 8;
			double cu = (u == 0) ? START_MUL : 1;
			double cv = (v == 0) ? START_MUL : 1;
			result += cu * cv * dequantized[j] * IDCT_CACHE[x][u] * IDCT_CACHE[y][v];
		}

		return (int) (result / 4);
	}

	static {
		for (int i = 0; i < 8; i++) {
			IDCT_CACHE[i] = new double[8];
			for (int j = 0; j < 8; j++) {
				IDCT_CACHE[i][j] = Math.cos((2 * i + 1) * j * Math.PI / 16);
			}
		}
	}

}
