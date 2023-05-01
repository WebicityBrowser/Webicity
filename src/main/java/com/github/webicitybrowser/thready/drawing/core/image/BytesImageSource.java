package com.github.webicitybrowser.thready.drawing.core.image;

public class BytesImageSource implements ImageSource {

	private final byte[] bytes;

	public BytesImageSource(byte[] bytes) {
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return this.bytes;
	}
	
}
