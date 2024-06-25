package com.github.webicitybrowser.codec.png.chunk.idat;

import com.github.webicitybrowser.codec.png.chunk.ihdr.IHDRChunkInfo;

public final class IDATUtil {
	
	private IDATUtil() {}

	public static float getColorBytes(IHDRChunkInfo ihdrChunkInfo) {
		int channelsPerPixel = switch (ihdrChunkInfo.colorType()) {
		case 0, 3 -> 1;
		case 2 -> 3;
		case 4 -> 2;
		case 6 -> 4;
		default -> throw new IllegalStateException("Unexpected value: " + ihdrChunkInfo.colorType());
		};

		return channelsPerPixel * ihdrChunkInfo.bitDepth() / 8f;
	}

}
