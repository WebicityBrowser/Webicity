package com.github.webicitybrowser.codec.png.chunk.plte;

import com.github.webicitybrowser.codec.png.exception.MalformedPNGException;

public final class PLTEChunkParser {
	
	private PLTEChunkParser() {}

	public static PLTEChunkInfo parse(byte[] data) throws MalformedPNGException {
		int paletteSize = data.length / 3;
		if (paletteSize * 3 != data.length) {
			throw new MalformedPNGException("Palette size must be a multiple of 3");
		}

		byte[][] palette = new byte[paletteSize][3];
		for (int i = 0; i < paletteSize; i++) {
			palette[i][0] = data[i * 3];
			palette[i][1] = data[i * 3 + 1];
			palette[i][2] = data[i * 3 + 2];
		}

		return new PLTEChunkInfo(palette);
	}

}
