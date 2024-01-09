package com.github.webicitybrowser.codec.png.chunk.ihdr;

public record IHDRChunkInfo(
	int width, int height, byte bitDepth, byte colorType,
	byte compressionMethod, byte filterMethod, byte interlaceMethod
) {
	
}
