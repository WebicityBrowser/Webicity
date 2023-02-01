package everyos.desktop.thready.core.positioning.util;

import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.RelativeDimension;
import everyos.desktop.thready.core.positioning.imp.AbsolutePositionImp;

public final class AbsolutePositionMath {
	
	private AbsolutePositionMath() {}
	
	public static AbsolutePosition sum(AbsolutePosition posA, AbsolutePosition posB) {
		return new AbsolutePositionImp(
			sumComponent(posA.getX(), posB.getX()),
			sumComponent(posA.getY(), posB.getY())
		);
	}
	
	public static float sumComponent(float dimA, float dimB) {
		return
			dimA == RelativeDimension.UNBOUNDED ? dimA :
			dimB == RelativeDimension.UNBOUNDED ? dimB :
			(dimA + dimB);
	}

}
