package com.github.webicitybrowser.thready.drawing.core.builder.imp;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;

public class Paint2DImp implements Paint2D {

	private final ColorFormat color;
	private final Font2D font;
	private float letterSpacing;

	public Paint2DImp(Paint2DBuilder builder) {
		this.color = builder.getColor();
		this.font = builder.getFont();
		this.letterSpacing = builder.getLetterSpacing();
	}

	@Override
	public ColorFormat getColor() {
		return this.color;
	}

	@Override
	public Font2D getFont() {
		return this.font;
	}

	@Override
	public float getLetterSpacing() {
		return this.letterSpacing;
	}

}
