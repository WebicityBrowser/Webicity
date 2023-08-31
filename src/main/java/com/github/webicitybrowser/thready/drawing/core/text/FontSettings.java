package com.github.webicitybrowser.thready.drawing.core.text;

import java.util.Arrays;
import java.util.Objects;

import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;

/**
 * Represents the appearance settings to load a font with.
 * @param fontSource A source that will provide glyph data.
 * @param fontSize The size of the font. Uses pixel measurements.
 * @param fontWeight The weight of the font (boldness/lightness).
 * @param fontDecorations Decorations to be applied to the font
 *  (such as italic or underlined)
 */
public record FontSettings(FontSource fontSource, float fontSize, int fontWeight, FontDecoration[] fontDecorations) {
	
	@Override
	public int hashCode() {
		return Objects.hash(fontSource, fontSize, fontWeight);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof FontSettings)) {
			return false;
		}
		
		FontSettings other = (FontSettings) o;
		return
			other.fontSource().equals(fontSource) &&
			other.fontSize() == fontSize &&
			other.fontWeight() == fontWeight &&
			Arrays.compare(other.fontDecorations(), fontDecorations) == 0;
	}
	
}