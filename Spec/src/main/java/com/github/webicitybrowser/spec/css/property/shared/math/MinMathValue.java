package com.github.webicitybrowser.spec.css.property.shared.math;

import java.util.List;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record MinMathValue(List<CSSValue> values) implements MathValue {

	@Override
	public String toString() {
		return "min(" + values + ")";
	}
	
}
