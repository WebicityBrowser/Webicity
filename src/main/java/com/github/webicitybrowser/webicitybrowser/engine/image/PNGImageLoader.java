package com.github.webicitybrowser.webicitybrowser.engine.image;

import com.github.webicitybrowser.webicity.core.image.ImageData;
import com.github.webicitybrowser.webicity.core.image.ImageFrame;
import com.github.webicitybrowser.webicity.core.image.ImageLoader;

public class PNGImageLoader implements ImageLoader {

	@Override
	public String[] getTypes() {
		return new String[] { "image/png" };
	}

	@Override
	public ImageData loadImage(byte[] data) {
		// As a test, we have a 2x2 black and purple RGBA squre for now
		byte[] pixels = new byte[] {
			0, 0, 0, (byte) 255,
			(byte) 255, 0, (byte) 255, (byte) 255,
			(byte) 255, 0, (byte) 255, (byte) 255,
			0, 0, 0, (byte) 255,
		};

		ImageFrame frame = new ImageFrame() {
			@Override public int getWidth() { return 2; }
			@Override public int getHeight() { return 2; }
			@Override public byte[] getBitmap() { return pixels; }
		};

		return () -> new ImageFrame[] { frame };
	}
	
}
