package com.github.webicitybrowser.thready.drawing.core;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;

/**
 * Paint2D allows you to set styling information
 * before drawing figures on a Canvas2D.
 */
public interface Paint2D {

	/**
	 * Get the color that figures will appear.
	 * @return The paint's color.
	 */
	ColorFormat getColor();

	/**
	 * Get the font that text will use.
	 * @return The paint's font.
	 */
	Font2D getFont();

	/**
	 * Get the letter spacing that text will use.
	 * @return The paint's letter spacing.
	 */
	float getLetterSpacing();
	
}
