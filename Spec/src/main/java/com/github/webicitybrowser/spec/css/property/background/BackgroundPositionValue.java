package com.github.webicitybrowser.spec.css.property.background;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record BackgroundPositionValue(BackgroundAxisPosition horizontalPosition, BackgroundAxisPosition verticalPosition) implements CSSValue {

	public static record BackgroundAxisPosition(BackgroundAxisReference reference, CSSValue offset) implements CSSValue {}

	public static enum BackgroundAxisReference implements CSSValue {
		LEFT, RIGHT, CENTER, TOP, BOTTOM
	}
	
}
