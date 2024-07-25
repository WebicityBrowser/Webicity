package com.github.webicitybrowser.spec.css.property.background;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record BackgroundRepeatValue(RepeatStyle repeatX, RepeatStyle repeatY) implements CSSValue {

	public static enum RepeatStyle {
		REPEAT, SPACE, ROUND, NO_REPEAT
	}
	
}
