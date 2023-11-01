package com.github.webicitybrowser.codec.png.chunk.idat;

import com.github.webicitybrowser.codec.png.chunk.ihdr.IHDRChunkInfo;
import com.github.webicitybrowser.codec.png.exception.UnsupportedPNGException;

public final class IDATUnfilter {

	private IDATUnfilter() {}

	public static byte[] unfilter(byte[] data, IHDRChunkInfo ihdrChunkInfo) throws UnsupportedPNGException {
		switch (ihdrChunkInfo.filterMethod()) {
		case 0:
			return unfilterDefault(data, ihdrChunkInfo);
		default:
			throw new UnsupportedPNGException();
		}
	}

	private static byte[] unfilterDefault(byte[] data, IHDRChunkInfo ihdrChunkInfo) {
		int height = ihdrChunkInfo.height();
		int bytesPerRow = determineBytesPerRow(ihdrChunkInfo);
		byte[] unfilteredData = new byte[data.length - height];
		int i = 0;
		int j = 0;
		for (int y = 0; y < height; y++) {
			int filterType = data[i++];
			for (int x = 0; x < bytesPerRow; x++) {
				int pixelLength = ihdrChunkInfo.bitDepth() < 8 ? 1 : Math.max(getColorBytes(ihdrChunkInfo), 1);
				int a = getByte(unfilteredData, x - pixelLength, y, bytesPerRow);
				int b = getByte(unfilteredData, x, y - 1, bytesPerRow);
				int c = getByte(unfilteredData, x - pixelLength, y - 1, bytesPerRow);
				unfilteredData[j++] = unfilterByte(filterType, data[i++] & 0xFF, a, b, c);
			}
		}

		return unfilteredData;
	}

	private static byte unfilterByte(int filterType, int x, int a, int b, int c) {
		return (byte) switch (filterType) {
		case 0 -> x;
		case 1 -> x + a;
		case 2 -> x + b;
		case 3 -> x + (a + b) / 2;
		case 4 -> x + paethPredictor(a, b, c);
		default -> throw new IllegalStateException("Unexpected value: " + filterType);
		};
	}

	private static int paethPredictor(int a, int b, int c) {
		int p = a + b - c;
		int pa = Math.abs(p - a);
		int pb = Math.abs(p - b);
		int pc = Math.abs(p - c);

		if (pa <= pb && pa <= pc) return a;
		if (pb <= pc) return b;
		return c;
	}

	private static int determineBytesPerRow(IHDRChunkInfo ihdrChunkInfo) {
		return ihdrChunkInfo.width() * getColorBytes(ihdrChunkInfo);
	}

	private static int getColorBytes(IHDRChunkInfo ihdrChunkInfo) {
		int channelsPerPixel = switch (ihdrChunkInfo.colorType()) {
		case 0, 3 -> 1;
		case 2 -> 3;
		case 4 -> 2;
		case 6 -> 4;
		default -> throw new IllegalStateException("Unexpected value: " + ihdrChunkInfo.colorType());
		};
		// TODO: This probably returns 0 for some combinations of bit depth and color type

		return channelsPerPixel * ihdrChunkInfo.bitDepth() / 8;
	}

	private static int getByte(byte[] data, int x, int y, int bytesPerRow) {
		if (x < 0 || y < 0) return 0;
		return data[y * bytesPerRow + x] & 0xFF;
	}

}
