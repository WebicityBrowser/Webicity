package com.github.webicitybrowser.spec.css.property.background;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public interface BackgroundSizeValue extends CSSValue {
	
	public static record RelativeBackgroundSizeValue(CSSValue sizeX, CSSValue sizeY) implements BackgroundSizeValue {}

	public static record CoverBackgroundSizeValue() implements BackgroundSizeValue {}

	public static record ContainBackgroundSizeValue() implements BackgroundSizeValue {}

}
