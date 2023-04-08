package com.github.webicitybrowser.thready.color.format;

/**
 * A ColorFormat representing a color stored in the RGBA8 scheme.
 * This uses 8-bit red, green, blue, and alpha channels.
 */
public interface RGBA8ColorFormat extends ColorFormat {

	/**
	 * Get the red channel of this color.
	 * @return The red channel of this color.
	 */
	int getRed();
	
	/**
	 * Get the green channel of this color.
	 * @return The green channel of this color.
	 */
	int getGreen();
	
	/**
	 * Get the blue channel of this color.
	 * @return The blue channel of this color.
	 */
	int getBlue();
	
	/**
	 * Get the alpha channel of this color.
	 * @return The alpha channel of this color.
	 */
	int getAlpha();
	
}