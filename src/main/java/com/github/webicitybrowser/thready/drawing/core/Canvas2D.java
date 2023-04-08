package com.github.webicitybrowser.thready.drawing.core;

/**
 * A Canvas2D is a surface that can be drawn on with 2-dimensional
 * figures.
 */
public interface Canvas2D {

	/**
	 * Draw a rectangle at the given points.
	 * @param x The leftmost x-coordinate of the rectangle.
	 * @param y The topmost y-coordinate of the rectangle.
	 * @param l The distance between the leftmost x-coordinate and
	 * 	rightmost x-coordinate of the rectangle.
	 * @param h The distance between the topmost y-coordinate and
	 * 	bottom-most y-coordinate of the rectangle.
	 */
	void drawRect(float x, float y, float l, float h);

	/**
	 * Draw text at the given points.
	 * @param x The leftmost x-coordinate of the text.
	 * @param y The topmost y-coordinate of the text.
	 * @param string The text to be drawn.
	 */
	void drawText(float x, float y, String string);

	/**
	 * Set the paint used to draw figures.
	 * @param paint The paint to be used when drawing figures.
	 */
	void setPaint(Paint2D paint);

}
