package com.github.webicitybrowser.thready.dimensions.util;

import java.util.function.BiFunction;

import com.github.webicitybrowser.thready.dimensions.AbsoluteDimensions;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;

public final class AbsoluteDimensionsMath {

/**
 * A util class that allows doing computations involving
 * absolute dimensions.
 */
private AbsoluteDimensionsMath() {}
	
	/**
	 * Return the sum of these dimensions.
	 * This is done by adding the x-values of both dimensions together,
	 * as well as the y-values. Unbounded dimensions are propagated to
	 * the final result.
	 * @param posA The first dimension to be summed.
	 * @param posB The second dimension to be summed.
	 * @param positionFactory A factory that creates a new dimension
	 * @return The final result.
	 */
	public static <T extends AbsoluteDimensions> T sum(T posA, T posB, BiFunction<Float, Float, T> dimensionFactory) {
		return dimensionFactory.apply(
			sumComponent(posA.xComponent(), posB.xComponent()),
			sumComponent(posA.yComponent(), posB.yComponent())
		);
	}
	
	/**
	 * Return the difference of these dimensions.
	 * This is done by subtracting the x-values of the dimensions,
	 * as well as the y-values. Unbounded dimensions are propagated to
	 * the final result.
	 * @param posA The dimension that is the minuend.
	 * @param posB The dimension that is the subtrahend.
	 * @param dimensionFactory A factory that creates a new dimension
	 * @return The final result.
	 */
	public static <T extends AbsoluteDimensions> T diff(T posA, T posB, BiFunction<Float, Float, T> dimensionFactory) {
		return dimensionFactory.apply(
			diffComponent(posA.xComponent(), posB.xComponent()),
			diffComponent(posA.yComponent(), posB.yComponent())
		);
	}
	
	private static float sumComponent(float dimA, float dimB) {
		return
			dimA == RelativeDimension.UNBOUNDED ? dimA :
			dimB == RelativeDimension.UNBOUNDED ? dimB :
			(dimA + dimB);
	}
	
	private static float diffComponent(float dimA, float dimB) {
		return
			dimA == RelativeDimension.UNBOUNDED ? dimA :
			dimB == RelativeDimension.UNBOUNDED ? dimB :
			(dimA - dimB);
	}
	
}
