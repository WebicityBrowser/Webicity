package everyos.desktop.thready.core.positioning.util;

import everyos.desktop.thready.core.positioning.RelativeDimension;

public final class DimensionComparisons {

	private DimensionComparisons() {}
	
	public static float min(float dimA, float dimB) {
		return
			dimA == RelativeDimension.UNBOUNDED ? dimB :
			dimB == RelativeDimension.UNBOUNDED ? dimA :
			Math.min(dimA, dimB);
	}
	
}
