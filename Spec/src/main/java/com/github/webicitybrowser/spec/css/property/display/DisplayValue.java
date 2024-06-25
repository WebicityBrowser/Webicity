package com.github.webicitybrowser.spec.css.property.display;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record DisplayValue(OuterDisplayType outerDisplayType, InnerDisplayType innerDisplayType) implements CSSValue {

}
