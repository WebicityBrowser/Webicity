package com.github.webicitybrowser.thready.drawing.core.text;

import java.util.Arrays;
import java.util.Objects;

import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;

public record FontSettings(FontSource fontSource, int fontSize, int fontWeight, FontDecoration[] fontDecorations) {
	
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