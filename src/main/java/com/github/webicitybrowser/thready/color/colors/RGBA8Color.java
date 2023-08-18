package com.github.webicitybrowser.thready.color.colors;

import com.github.webicitybrowser.thready.color.RawColor;
import com.github.webicitybrowser.thready.color.format.RGBA8ColorFormat;
import com.github.webicitybrowser.thready.color.imp.InternalColorImp;

/**
 * An implementation of the RGBA8ColorFormat.
 * You can set 8-bit values for the red, green, blue
 * and alpha components of an RGBA8-scheme color scheme.
 */
public class RGBA8Color implements RGBA8ColorFormat {

	private final int color;

	private static final int MAX_COLOR_COMPONENT_VALUE = 255;
	private static final int RED_MASK = 0xFF000000;
	private static final int GREEN_MASK = 0x00FF0000;
	private static final int BLUE_MASK = 0x0000FF00;
	private static final int ALPHA_MASK = 0x000000FF;

	public RGBA8Color(int r, int g, int b, int a) {
		color = (r << 24) | (g << 16) | (b << 8) | a;
	}

	public RGBA8Color(int r, int g, int b) {
		this(r, g, b, MAX_COLOR_COMPONENT_VALUE);
	}

	@Override
	public int getRed() {
		return (color & RED_MASK) >>> 24;
	}

	@Override
	public int getGreen() {
		return (color & GREEN_MASK) >>> 16;
	}

	@Override
	public int getBlue() {
		return (color & BLUE_MASK) >>> 8;
	}

	@Override
	public int getAlpha() {
		return color & ALPHA_MASK;
	}

	@Override
	public RawColor toRawColor() {
		return InternalColorImp.ofRGBA8(getRed(), getGreen(), getBlue(), getAlpha());
	}

	@Override
	public String toString() {
		return String.format("Color [a=%d, r=%d, g=%d, b=%d]", getAlpha(), getRed(), getGreen(), getBlue());
	}

}
