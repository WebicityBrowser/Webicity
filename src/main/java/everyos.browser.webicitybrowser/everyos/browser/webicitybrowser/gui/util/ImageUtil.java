package everyos.browser.webicitybrowser.gui.util;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicity.lace.basics.pipeline.paint.canvas.shapes.ImageBuffer;

public final class ImageUtil {
	
	private ImageUtil() {}

	public static ImageBuffer loadImageFromResource(String name) {
		try(InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(name)) {
			ImageBuffer buffer = new ImageBuffer(inputStream.readAllBytes());
			inputStream.close();
			
			return buffer;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
