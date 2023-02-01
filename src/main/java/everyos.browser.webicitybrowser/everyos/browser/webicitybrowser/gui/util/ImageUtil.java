package everyos.browser.webicitybrowser.gui.util;

import java.io.IOException;
import java.io.InputStream;

import everyos.desktop.thready.core.graphics.image.Image;
import everyos.desktop.thready.core.graphics.image.imp.BytesImage;

public final class ImageUtil {

	private ImageUtil() {}

	public static Image loadImageFromResource(String name) {
		try(InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(name)) {
			Image buffer = new BytesImage(inputStream.readAllBytes());
			inputStream.close();
			
			return buffer;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
