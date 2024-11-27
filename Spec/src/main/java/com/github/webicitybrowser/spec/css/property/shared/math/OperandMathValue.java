package com.github.webicitybrowser.spec.css.property.shared.math;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record OperandMathValue(Operand operand, CSSValue leftSide, CSSValue rightSide) implements MathValue {

	@Override
	public String toString() {
		return "(" + leftSide + " " + operand + " " + rightSide + ")";
	}

	public static enum Operand {
		PLUS, MINUS, MULTIPLY, DIVIDE
	}
	
}
