package com.github.webicitybrowser.thready.drawing.skija.imp;

import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;

import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMetrics;

public class SkijaFontMetricsImp implements com.github.webicitybrowser.thready.drawing.core.text.FontMetrics {

	private final FontSettings settings;
	private final FontMetrics primaryMetrics;
	private final Font[] fonts;
	
	private final float[] widthCache = new float[256];

	public SkijaFontMetricsImp(Font[] fonts, FontSettings settings) {
		this.primaryMetrics = fonts[0].getMetrics();
		this.settings = settings;
		this.fonts = fonts;
	}

	@Override
	public float getCharacterWidth(int codePoint) {
		if (codePoint < 256 && widthCache[codePoint] != 0) {
			return widthCache[codePoint];
		}
		
		float width = 0;
		for (int i = 0; i < fonts.length; i++) {
			short glyph = fonts[i].getUTF32Glyph(codePoint);
			if (glyph == 0 && i != 0) continue;
			width = fonts[i].getWidths(new short[] { glyph })[0];
			if (glyph != 0) break;
		}

		if (codePoint < 256) {
			widthCache[codePoint] = width;
		}
		
		return width;
	}
	
	@Override
	public float getStringWidth(String text) {
		float textWidth = 0;
		for (int i = 0; i < text.length(); i++) {
			int codePoint = text.codePointAt(i);
			textWidth += getCharacterWidth(codePoint);
		}
		
		return textWidth;
	}

	@Override
	public float getHeight() {
		return primaryMetrics.getHeight();
	}

	@Override
	public float getLeading() {
		return primaryMetrics.getLeading();
	}
	
	@Override
	public float getDescent() {
		return primaryMetrics.getDescent();
	}
	
	@Override
	public float getAscent() {
		return primaryMetrics.getAscent();
	}
	
	@Override
	public float getCapHeight() {
		return primaryMetrics.getCapHeight();
	}

	@Override
	public float getXHeight() {
		return primaryMetrics.getXHeight();
	}

	@Override
	public int getWeight() {
		return settings.fontWeight();
	}

	@Override
	public float getSize() {
		return settings.fontSize();
	}

}
