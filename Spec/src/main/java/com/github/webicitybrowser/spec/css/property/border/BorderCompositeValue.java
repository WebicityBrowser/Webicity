package com.github.webicitybrowser.spec.css.property.border;

import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public record BorderCompositeValue(CSSValue width, BorderStyleValue style, ColorValue color) implements CSSValue {
	
}
