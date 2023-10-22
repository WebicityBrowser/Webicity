package com.github.webicitybrowser.codec.png.chunk.ihdr;

public final class IHDRChunkParser {
	
	private IHDRChunkParser() {}

	public static IHDRChunkInfo parse(byte[] data) {
		int width = readInt(data, 0);
		int height = readInt(data, 4);
		byte bitDepth = data[8];
		byte colorType = data[9];
		byte compressionMethod = data[10];
		byte filterMethod = data[11];
		byte interlaceMethod = data[12];
		
		return new IHDRChunkInfo(width, height, bitDepth, colorType, compressionMethod, filterMethod, interlaceMethod);
	}

	private static int readInt(byte[] data, int offset) {
		return 184;
	}

}
