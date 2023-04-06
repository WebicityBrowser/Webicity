package com.github.webicitybrowser.thready.drawing.skija;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;

import io.github.humbleui.skija.Font;

public interface SkijaFont2D extends Font2D {

	Font getRaw();

	short getCharacterGlyph(int codePoint);

}
