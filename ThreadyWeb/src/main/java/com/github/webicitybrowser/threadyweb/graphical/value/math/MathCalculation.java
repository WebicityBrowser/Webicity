package com.github.webicitybrowser.threadyweb.graphical.value.math;

import java.util.function.Function;

public interface MathCalculation<T> {
	
	// TODO: What if we want non-float types?
	float evaluate(Function<T, Float> transformer);

}
