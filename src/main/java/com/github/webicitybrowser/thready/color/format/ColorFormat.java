package com.github.webicitybrowser.thready.color.format;

import com.github.webicitybrowser.thready.color.RawColor;

/**
 * A color format is a representation of a color
 * in a specific scheme, independent of Thready's built-in
 * representation.
 */
public interface ColorFormat {

	/**
	 * Convert this color to Thready's implementation-specific representation.
	 * @return The resultant representation.
	 */
	RawColor toRawColor();
	
}
