package everyos.desktop.thready.core.graphics.image.imp;

import everyos.desktop.thready.core.graphics.image.Image;

public class BytesImage implements Image {

	private final byte[] bytes;

	public BytesImage(byte[] bytes) {
		this.bytes = bytes;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

}
