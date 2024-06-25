package com.github.webicitybrowser.thready.drawing.core.text;

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
public record FontSettings(FontSource[] fontSources, float fontSize, int fontWeight, FontDecoration[] fontDecorations) {
	
	@Override
	public int hashCode() {
		return Objects.hash(fontSize, fontWeight);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof FontSettings other) {
			return
				arrayEquals(fontSources, other.fontSources) &&
				other.fontSize() == fontSize &&
				other.fontWeight() == fontWeight &&
				arrayEquals(other.fontDecorations(), fontDecorations);
		}

		return false;
	}

	private boolean arrayEquals(Object[] array1 , Object[] array2) {
		if (array1 == array2) return true;
		if (array1.length != array2.length) return false;

		for (int i = 0; i < array1.length; i++) {
			if (!array1[i].equals(array2[i])) {
				return false;
			}
		}

		return true;
	}
	
}