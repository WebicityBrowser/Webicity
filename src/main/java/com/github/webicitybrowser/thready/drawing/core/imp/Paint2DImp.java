package com.github.webicitybrowser.thready.drawing.core.imp;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;

public class Paint2DImp implements Paint2D {

private final ColorFormat color;
	
	private final Font2D font;

	public Paint2DImp(Paint2DBuilder builder) {
		this.color = builder.getColor();
		this.font = builder.getFont();
	}

	@Override
	public ColorFormat getColor() {
		return this.color;
	}

	@Override
	public Font2D getLoadedFont() {
		return this.font;
	}

}
