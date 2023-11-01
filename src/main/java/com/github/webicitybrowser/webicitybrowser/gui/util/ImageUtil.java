package com.github.webicitybrowser.webicitybrowser.gui.util;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.thready.drawing.core.image.EncodedBytesImageSource;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;

public final class ImageUtil {

	private ImageUtil() {}

	public static ImageSource loadImageFromResource(String name) {
		try(InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(name)) {
			ImageSource buffer = new EncodedBytesImageSource(inputStream.readAllBytes());
			inputStream.close();
			
			return buffer;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
