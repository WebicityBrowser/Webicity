package com.github.webicitybrowser.thready.drawing.core;

import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;

/**
 * A resource loader allows for loading resources required
 * to draw to a surface.
 */
public interface ResourceLoader {

	/**
	 * Load an image that can be drawn.
	 * @param source The data to construct the image from.
	 * @return The loaded image.
	 */
	Image loadImage(ImageSource source);
	
	/**
	 * Load a font that can be used to draw text.
	 * @param settings The appearance settings to use when drawing the font.
	 * @return The loaded font.
	 */
	Font2D loadFont(FontSettings settings);
	
}
