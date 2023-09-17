package com.github.webicitybrowser.spec.css.property.border.color;

import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public record BorderColorValue(ColorValue left, ColorValue right, ColorValue top, ColorValue bottom) implements CSSValue {
	
}
