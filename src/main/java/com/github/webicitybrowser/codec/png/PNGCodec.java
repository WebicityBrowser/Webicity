package com.github.webicitybrowser.codec.png;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import com.github.webicitybrowser.codec.image.ImageCodec;
import com.github.webicitybrowser.codec.image.ImageData;
import com.github.webicitybrowser.codec.image.ImageFrame;
import com.github.webicitybrowser.codec.image.ImageProgressiveCallback;
import com.github.webicitybrowser.codec.image.PossibleImage;
import com.github.webicitybrowser.codec.png.exception.MalformedPNGException;

public class PNGCodec implements ImageCodec {

	@Override
	public String[] getTypes() {
		return new String[] { "image/png" };
	}

	@Override
	public PossibleImage loadImage(byte[] data, ImageProgressiveCallback callback) {
		PNGReader pngReader = new PNGReader();

		try {
			PNGResult pngResult = pngReader.read(new ByteArrayInputStream(data));
			ImageFrame[] frames = new ImageFrame[1];
			frames[0] = new PNGFrame(pngResult.width(), pngResult.height(), pngResult.pixels());
			return new PossibleImage(
				Optional.of(new PNGData(frames)),
				Optional.empty());
		} catch (MalformedPNGException | IOException e) {
			return new PossibleImage(
				Optional.empty(),
				Optional.of(e));
		}
	}
	
	private static record PNGFrame(int width, int height, byte[] bitmap) implements ImageFrame {}

	private static record PNGData(ImageFrame[] frames) implements ImageData {}

}
