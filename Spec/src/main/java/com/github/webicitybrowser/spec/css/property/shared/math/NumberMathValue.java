package com.github.webicitybrowser.spec.css.property.shared.math;

public record NumberMathValue(Number value) implements MathValue {
	
	@Override
	public String toString() {
		return value.toString();
	}

}
