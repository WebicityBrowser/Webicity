package com.github.webicitybrowser.thready.drawing.core;

import com.github.webicitybrowser.thready.drawing.core.image.Image;

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
	 * Draw an ellipse at the given points.
	 * @param x The leftmost x-coordinate of the bounding box of the ellipse.
	 * @param y The topmost y-coordinate of the bounding box of the ellipse.
	 * @param l The distance between the leftmost x-coordinate and
	 * 	rightmost x-coordinate of the bounding box of the ellipse.
	 * @param h The distance between the topmost y-coordinate and
	 * 	bottom-most y-coordinate of the bounding box of the ellipse.
	 */
	void drawEllipse(float x, float y, float l, float h);

	/**
	 * Draw text at the given points.
	 * @param x The leftmost x-coordinate of the text.
	 * @param y The topmost y-coordinate of the text.
	 * @param string The text to be drawn.
	 */
	void drawText(float x, float y, String string);
	
	void drawLine(float x, float y, float run, float fall);
	
	void drawTexture(float x, float y, float l, float h, Image texture);

	Canvas2D createTranslatedCanvas(float x, float y);
	
	/**
	 * Get the paint used to draw figures.
	 * @return paint The paint used when drawing figures.
	 */
	Paint2D getPaint();

	/**
	 * Get a copy of this canvas that uses a specific paint.
	 * @param paint The paint to be used.
	 * @return The canvas using the specific paint
	 */
	Canvas2D withPaint(Paint2D paint);


	Canvas2D createClippedCanvas(float x, float y, float width, float height, ChildCanvasSettings childCanvasSettings);

}
