package com.github.webicitybrowser.spec.css.property.border;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record MultiBorderWidthValue(CSSValue left, CSSValue right, CSSValue top, CSSValue bottom) implements CSSValue {
	
}
