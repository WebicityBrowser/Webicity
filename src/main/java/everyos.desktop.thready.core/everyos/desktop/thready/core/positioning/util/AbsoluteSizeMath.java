package everyos.desktop.thready.core.positioning.util;

import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.RelativeDimension;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;

public final class AbsoluteSizeMath {

	private AbsoluteSizeMath() {}
	
	public static AbsoluteSize min(AbsoluteSize sizeA, AbsoluteSize sizeB) {
		return new AbsoluteSizeImp(
			DimensionComparisons.min(sizeA.getWidth(), sizeB.getWidth()),
			DimensionComparisons.min(sizeA.getHeight(), sizeB.getHeight())
		);
	}

	public static AbsoluteSize sum(AbsoluteSize sizeA, AbsoluteSize sizeB) {
		return new AbsoluteSizeImp(
			sumComponent(sizeA.getWidth(), sizeB.getWidth()),
			sumComponent(sizeA.getHeight(), sizeB.getHeight())
		);
	}
	
	public static float sumComponent(float dimA, float dimB) {
		return
			dimA == RelativeDimension.UNBOUNDED ? dimA :
			dimB == RelativeDimension.UNBOUNDED ? dimB :
			(dimA + dimB);
	}
	
}
