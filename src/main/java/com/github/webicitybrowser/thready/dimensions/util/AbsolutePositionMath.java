package com.github.webicitybrowser.thready.dimensions.util;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;

public final class AbsolutePositionMath {

private AbsolutePositionMath() {}
	
	public static AbsolutePosition sum(AbsolutePosition posA, AbsolutePosition posB) {
		return new AbsolutePosition(
			sumComponent(posA.x(), posB.x()),
			sumComponent(posA.y(), posB.y())
		);
	}
	
	public static AbsolutePosition diff(AbsolutePosition posA, AbsolutePosition posB) {
		return new AbsolutePosition(
			sumComponent(posA.x(), -posB.x()),
			sumComponent(posA.y(), -posB.y())
		);
	}
	
	public static float sumComponent(float dimA, float dimB) {
		return
			dimA == RelativeDimension.UNBOUNDED ? dimA :
			dimB == RelativeDimension.UNBOUNDED ? dimB :
			(dimA + dimB);
	}
	
}
