package com.github.webicitybrowser.spec.css.property.margin;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record MarginValue(CSSValue left, CSSValue right, CSSValue top, CSSValue bottom) implements CSSValue {

}
