package com.github.webicitybrowser.thready.drawing.core.text;

/**
 * A loaded font that is able to be used to draw text.
 */
public interface Font2D {

	/**
	 * Returns info about the sizing and statistics of this font.
	 * @return The metrics of this font.
	 */
	FontMetrics getMetrics();

	/**
	 * Get the font settings that this font was loaded with.
	 * @return The font settings.
	 */
	FontSettings getSettings();

}
