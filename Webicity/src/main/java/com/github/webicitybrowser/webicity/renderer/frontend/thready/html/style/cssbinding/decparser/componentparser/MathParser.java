package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.componentparser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MaxMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MinMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.NumberMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.OperandMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.OperandMathValue.Operand;
import com.github.webicitybrowser.threadyweb.graphical.value.math.MathCalculation;

public final class MathParser {
	
	private MathParser() {}

	public static <T> MathCalculation<T> parseMathValue(MathValue value, Function<CSSValue, T> innerParser) {
		if (value instanceof MinMathValue minMathValue) {
			List<Object> values = convertMany(minMathValue.values(), innerParser);
			return minValue(values);
		} else if (value instanceof MaxMathValue maxMathValue) {
			List<Object> values = convertMany(maxMathValue.values(), innerParser);
			return maxValue(values);
		} else if (value instanceof OperandMathValue operandMathValue) {
			Operand operand = operandMathValue.operand();
			Object leftSide = convert(operandMathValue.leftSide(), innerParser);
			Object rightSide = convert(operandMathValue.rightSide(), innerParser);
			return operandValue(operand, leftSide, rightSide);
		} else if (value instanceof NumberMathValue numberMathValue) {
			return transformer -> numberMathValue.value().floatValue();
		} else {
			throw new IllegalArgumentException("Unknown MathValue type: " + value.getClass().getName());
		}
	}


	private static <T> MathCalculation<T> minValue(List<Object> values) {
		return transformer -> {
			List<Float> applied = applyMany(values, transformer);
			return applied.stream().min(Float::compareTo).orElseThrow();
		};
	}

	private static <T> MathCalculation<T> maxValue(List<Object> values) {
		return transformer -> {
			List<Float> applied = applyMany(values, transformer);
			return applied.stream().max(Float::compareTo).orElseThrow();
		};
	}

	private static <T> MathCalculation<T> operandValue(Operand operand, Object leftSide, Object rightSide) {
		return transformer -> {
			float left = apply(leftSide, transformer);
			float right = apply(rightSide, transformer);
			return switch (operand) {
				case PLUS -> left + right;
				case MINUS -> left - right;
				case MULTIPLY -> left * right;
				case DIVIDE -> left / right;
			};
		};
	}

	private static <T> List<Float> applyMany(List<Object> values, Function<T, Float> transformer) {
		List<Float> applied = new ArrayList<>(values.size());
		for (Object value : values) {
			applied.add(apply(value, transformer));
		}
		
		return applied;
	}

	@SuppressWarnings("unchecked")
	private static <T> Float apply(Object value, Function<T, Float> transformer) {
		if (value instanceof Number number) {
			return (Float) number;
		} else if (value instanceof MathCalculation<?>) {
			return ((MathCalculation<T>) value).evaluate(transformer);
		} else {
			// Hopefully the right type, will result in an exception otherwise
			return transformer.apply((T) value);
		}
	}

	private static <T> List<Object> convertMany(List<CSSValue> values, Function<CSSValue, T> innerParser) {
		List<Object> converted = new ArrayList<>(values.size());
		for (CSSValue value : values) {
			converted.add(convert(value, innerParser));
		}
		
		return converted;
	}

	private static <T> Object convert(CSSValue value, Function<CSSValue, T> innerParser) {
		if (value instanceof MathValue mathValue) {
			return parseMathValue(mathValue, innerParser);
		} else {
			return innerParser.apply(value);
		}
	}

}
