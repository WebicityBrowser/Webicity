package com.github.webicitybrowser.spec.css.property.border;

import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public record MultiBorderColorValue(ColorValue left, ColorValue right, ColorValue top, ColorValue bottom) implements CSSValue {
	
}
