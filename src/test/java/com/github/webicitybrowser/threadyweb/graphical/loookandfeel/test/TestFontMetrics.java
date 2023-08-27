package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test;

import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;

public class TestFontMetrics implements FontMetrics {

	@Override
	public float getCharacterWidth(int codePoint) {
		return 8;
	}

	@Override
	public float getHeight() {
		return 6;
	}

	@Override
	public float getCapHeight() {
		return 12;
	}

	@Override
	public float getStringWidth(String text) {
		return text.length() * 8;
	}

	@Override
	public float getLeading() {
		return 4;
	}

	@Override
	public float getDescent() {
		return 2;
	}

	@Override
	public float getAscent() {
		return 6;
	}
	
}
