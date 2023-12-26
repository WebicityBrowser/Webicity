package com.github.webicitybrowser.codec.jpeg.chunk.sos;

public record SOSChunkInfo(SOSComponentInfo[] components) {
	
	public static record SOSComponentInfo(int componentId, int dcCodingTableSelector, int acCodingTableSelector) {}

}
