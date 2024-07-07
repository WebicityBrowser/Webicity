package com.github.webicitybrowser.threadyweb.graphical.value;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;

public interface SizeCalculation {
	
	static SizeCalculation SIZE_AUTO = (_1, _2) -> RelativeDimension.UNBOUNDED;
	static SizeCalculation SIZE_ZERO = (_1, _2) -> 0;

	 float calculate(SizeCalculationContext context, boolean isHorizontal);

	static record SizeCalculationContext(
		AbsoluteSize parentSize, AbsoluteSize viewportSize, FontMetrics relativeFont, FontMetrics rootFont
	) {}

}
