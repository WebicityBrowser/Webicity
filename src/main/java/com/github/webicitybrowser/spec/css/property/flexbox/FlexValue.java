package com.github.webicitybrowser.spec.css.property.flexbox;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record FlexValue(FlexFactorValue growFactor, FlexFactorValue shrinkFactor, FlexBasisValue basis) implements CSSValue {
	
}
