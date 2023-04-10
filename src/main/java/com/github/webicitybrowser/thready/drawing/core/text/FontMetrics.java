package com.github.webicitybrowser.thready.drawing.core.text;

/**
 * The metrics of this font provides sizings and statistics
 * about this font. To see what various stats mean, look up a
 * font metric diagram online.
 */
public interface FontMetrics {

	/**
	 * Get the width of a specified character.
	 * @param codePoint The unicode codepoint of the character.
	 * @return The width of the specified character.
	 */
	float getCharacterWidth(int codePoint);
	
	/**
	 * Get the height of characters.
	 * @return The height of characters.
	 */
	float getHeight();

	/**
	 * Get the font's leading value.
	 * @return The font's leading value.
	 */
	float getLeading();

	/**
	 * Get the width of a specified string.
	 * @param text The text to get the width of.
	 * @return The width of the specified text.
	 */
	float getStringWidth(String text);

	/**
	 * Get the font's descent.
	 * @return The font's descent.
	 */
	float getDescent();

	/**
	 * Get the font's ascent.
	 * @return The font's ascent.
	 */
	float getAscent();

	/**
	 * Get the font's cap height.
	 * @return The font's cap height.
	 */
	float getCapHeight();

}