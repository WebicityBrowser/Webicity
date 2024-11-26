package com.github.webicitybrowser.spec.css.property.shared.math;

import java.util.List;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record MaxMathValue(List<CSSValue> values) implements MathValue {

	@Override
	public String toString() {
		StringBuilder valueStr = new StringBuilder();
		for (int i = 0; i < values.size(); i++) {
			if (i != 0) {
				valueStr.append(", ");
			}
			valueStr.append(values.get(i));
		}
		return "max(" + valueStr + ")";
	}
	
}
