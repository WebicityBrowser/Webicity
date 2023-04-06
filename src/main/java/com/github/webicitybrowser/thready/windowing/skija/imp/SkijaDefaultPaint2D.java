package com.github.webicitybrowser.thready.windowing.skija.imp;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;

public class SkijaDefaultPaint2D implements Paint2D {

	@Override
	public ColorFormat getColor() {
		return Colors.WHITE;
	}

	@Override
	public Font2D getLoadedFont() {
		return null;
	}

	

}
