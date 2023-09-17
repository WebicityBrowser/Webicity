package com.github.webicitybrowser.spec.css.property.border.width;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record BorderWidthValue(CSSValue left, CSSValue right, CSSValue top, CSSValue bottom) implements CSSValue {
	
}
