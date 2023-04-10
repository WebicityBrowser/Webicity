package com.github.webicitybrowser.thready.drawing.core.builder;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.imp.Paint2DImp;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;

/**
 * Allows construction of a Paint2D object.
 */
public class Paint2DBuilder {
	
	private ColorFormat color;
	private Font2D font;
	
	/**
	 * Set the color of the paint to be created.
	 * @param color The color of the paint to be created.
	 * @return This builder.
	 */
	public Paint2DBuilder setColor(ColorFormat color) {
		this.color = color;

		return this;
	}
	
	/**
	 * Get the color of the paint to be created.
	 * @return The color of the paint to be created.
	 */
	public ColorFormat getColor() {
		return this.color;
	}
	
	/**
	 * Set the font of the paint to be created.
	 * @param font The font of the paint to be created.
	 * @return This builder.
	 */
	public Paint2DBuilder setFont(Font2D font) {
		this.font = font;

		return this;
	}
	
	/**
	 * Get the font of the paint to be created.
	 * @return The font of the paint to be created.
	 */
	public Font2D getFont() {
		return this.font;
	}
	
	/**
	 * Create a Paint2D object using this builder's configurations.
	 * @return The built Paint2D object.
	 */
	public Paint2D build() {
		return new Paint2DImp(this);
	}
	
	/**
	 * Create a Paint2DBuilder with some fields pre-initialized
	 * by an old paint.
	 * @param paint The paint used to pre-initialize fields.
	 * @return The new Paint2DBuilder.
	 */
	public static Paint2DBuilder clone(Paint2D paint) {
		return new Paint2DBuilder()
			.setColor(paint.getColor())
			.setFont(paint.getFont());
	}
	
}