package com.github.webicitybrowser.thready.dimensions.util;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;

public final class AbsolutePositionMath {

/**
 * A util class that allows doing computations involving
 * absolute positions.
 */
private AbsolutePositionMath() {}
	
	/**
	 * Return the sum of these positions.
	 * This is done by adding the x-values of both positions together,
	 * as well as the y-values. Unbounded positions are propagated to
	 * the final result.
	 * @param posA The first position to be summed.
	 * @param posB The second position to be summed.
	 * @return The final result.
	 */
	public static AbsolutePosition sum(AbsolutePosition posA, AbsolutePosition posB) {
		return new AbsolutePosition(
			sumComponent(posA.x(), posB.x()),
			sumComponent(posA.y(), posB.y())
		);
	}
	
	/**
	 * Return the difference of these positions.
	 * This is done by subtracting the x-values of the positions,
	 * as well as the y-values. Unbounded positions are propagated to
	 * the final result.
	 * @param posA The position that is the minuend.
	 * @param posB The position that is the subtrahend.
	 * @return The final result.
	 */
	public static AbsolutePosition diff(AbsolutePosition posA, AbsolutePosition posB) {
		return new AbsolutePosition(
			diffComponent(posA.x(), posB.x()),
			diffComponent(posA.y(), posB.y())
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
