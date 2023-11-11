package com.github.webicitybrowser.codec.jpeg;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import com.github.webicitybrowser.codec.image.ImageCodec;
import com.github.webicitybrowser.codec.image.ImageData;
import com.github.webicitybrowser.codec.image.ImageFrame;
import com.github.webicitybrowser.codec.image.ImageProgressiveCallback;
import com.github.webicitybrowser.codec.image.PossibleImage;
import com.github.webicitybrowser.codec.jpeg.exception.MalformedJPEGException;

public class JPEGCodec implements ImageCodec {

	@Override
	public String[] getTypes() {
		return new String[] { "image/jpeg" };
	}

	@Override
	public PossibleImage loadImage(byte[] data, ImageProgressiveCallback callback) {
		JPEGReader jpgReader = new JPEGReader();

		try {
			JPEGResult jpgResult = jpgReader.read(new ByteArrayInputStream(data));
			ImageFrame[] frames = new ImageFrame[1];
			frames[0] = new JPEGFrame(jpgResult.width(), jpgResult.height(), jpgResult.pixels());
			return new PossibleImage(
				Optional.of(new JPEGData(frames)),
				Optional.empty());
		} catch (MalformedJPEGException | IOException e) {
			return new PossibleImage(
				Optional.empty(),
				Optional.of(e));
		}
	}

	private static record JPEGFrame(int width, int height, byte[] bitmap) implements ImageFrame {}

	private static record JPEGData(ImageFrame[] frames) implements ImageData {}
	
}
