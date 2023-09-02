package com.github.webicitybrowser.spec.css.property.padding;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record PaddingValue(CSSValue left, CSSValue right, CSSValue top, CSSValue bottom) implements CSSValue {

}
