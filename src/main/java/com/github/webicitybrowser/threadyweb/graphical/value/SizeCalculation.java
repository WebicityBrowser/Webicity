package com.github.webicitybrowser.threadyweb.graphical.value;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;

public interface SizeCalculation {
	
	public static SizeCalculation SIZE_AUTO = context -> RelativeDimension.UNBOUNDED;

    float calculate(SizeCalculationContext context);

	static record SizeCalculationContext(
		AbsoluteSize parentSize, AbsoluteSize viewportSize, FontMetrics relativeFont, boolean isHorizontal
	) {}

}
