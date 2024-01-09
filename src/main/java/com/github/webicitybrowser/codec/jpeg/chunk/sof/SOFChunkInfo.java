package com.github.webicitybrowser.codec.jpeg.chunk.sof;

public record SOFChunkInfo(int width, int height, SOFComponentInfo[] components) {
	
	public static record SOFComponentInfo(
		int componentId, int horizontalSamplingFactor, int verticalSamplingFactor, int quantizationTableSelector
	) {}

}
