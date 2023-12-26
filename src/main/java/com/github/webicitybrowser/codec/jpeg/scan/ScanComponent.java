package com.github.webicitybrowser.codec.jpeg.scan;

public record ScanComponent(int componentId, EntropyDecoder entropyDecoder, int hSample, int vSample) {
	
}
