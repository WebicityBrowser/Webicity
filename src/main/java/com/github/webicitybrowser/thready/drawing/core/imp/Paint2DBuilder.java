package com.github.webicitybrowser.thready.drawing.core.imp;

import com.github.webicitybrowser.thready.color.ColorFormat;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;

public class Paint2DBuilder {
	
	private ColorFormat color;
	private Font2D font;
	
	public Paint2DBuilder setColor(ColorFormat color) {
		this.color = color;

		return this;
	}
	
	public ColorFormat getColor() {
		return this.color;
	}
	
	public Paint2DBuilder setLoadedFont(Font2D font) {
		this.font = font;

		return this;
	}
	
	public Font2D getFont() {
		return this.font;
	}
	
	public Paint2D build() {
		return new Paint2DImp(this);
	}
	
	public static Paint2DBuilder clone(Paint2D paint) {
		return new Paint2DBuilder()
			.setColor(paint.getColor())
			.setLoadedFont(paint.getLoadedFont());
	}
	
}