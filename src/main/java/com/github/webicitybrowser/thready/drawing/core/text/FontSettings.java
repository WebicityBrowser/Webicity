package com.github.webicitybrowser.thready.drawing.core.text;

import java.util.Arrays;
import java.util.Objects;

public record FontSettings(int fontSize, int fontWeight, FontDecoration[] fontDecorations) {
	
	@Override
	public int hashCode() {
		return Objects.hash(fontSize, fontWeight);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof FontSettings)) {
			return false;
		}
		
		FontSettings other = (FontSettings) o;
		return
			other.fontSize() == fontSize &&
			other.fontWeight() == fontWeight &&
			Arrays.compare(other.fontDecorations(), fontDecorations) == 0;
	}
	
}