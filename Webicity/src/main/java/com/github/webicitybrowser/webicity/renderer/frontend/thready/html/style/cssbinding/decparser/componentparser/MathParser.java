package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.componentparser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MaxMathValue;
import com.github.webicitybrowser.spec.css.property.shared.math.MinMathValue;
import com.github.webicitybrowser.threadyweb.graphical.value.math.MathCalculation;

public final class MathParser {
	
	private MathParser() {}

	public static <U> MathCalculation<U> parseMathValue(MathValue value, Function<CSSValue, U> innerParser) {
		if (value instanceof MinMathValue minMathValue) {
			List<Object> values = convertMany(minMathValue.values(), innerParser);
			return minValue(values);
		} else if (value instanceof MaxMathValue maxMathValue) {
			List<Object> values = convertMany(maxMathValue.values(), innerParser);
			return maxValue(values);
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

	@SuppressWarnings("unchecked")
	private static <U> List<Float> applyMany(List<Object> values, Function<U, Float> transformer) {
		List<Float> applied = new ArrayList<>(values.size());
		for (Object value : values) {
			if (value instanceof Number number) {
				applied.add((Float) number);
			} else {
				// Hopefully the right type, will result in an exception otherwise
				applied.add(transformer.apply((U) value));
			}
		}
		
		return applied;
	}

	private static <T> List<Object> convertMany(List<CSSValue> values, Function<CSSValue, T> innerParser) {
		List<Object> converted = new ArrayList<>(values.size());
		for (CSSValue value : values) {
			converted.add(innerParser.apply(value));
		}
		
		return converted;
	}

}
