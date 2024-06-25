package com.github.webicitybrowser.spec.css.parser.property.color;

import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class RGBColorValueImp implements ColorValue {
	
	private final int color;

	public RGBColorValueImp(int r, int g, int b, int a) {
		color = (r << 24) + (g << 16) + (b << 8) + a;
	}

	public RGBColorValueImp(int r, int g, int b) {
		this(r, g, b, 255);
	}

	
	@Override
	public int getRed() {
		return (color >>> 24) & 0xFF;
	}
	
	@Override
	public int getGreen() {
		return (color >>> 16) & 255;
	}
	
	@Override
	public int getBlue() {
		return (color >>> 8) & 255;
	}
	
	@Override
	public int getAlpha() {
		return color & 255;
	}

}
