package com.github.webicitybrowser.threadyweb.graphical.layout.adjusted.position;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionTypeDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.PositionType;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class PositionOffsetUtil {
	
	private PositionOffsetUtil() {}

	public static AbsolutePosition getRelativePositionOffset(SizeCalculationContext sizeCalculationContext, DirectivePool styleDirectives) {
		// TODO: Properly handle percentages
		float[] positions = PositionOffsetCalculations.calculateOffset(sizeCalculationContext, styleDirectives);

		return PositionOffsetCalculations.calculateRelativePositionOffset(positions);
	}

	public static AbsolutePosition getFixedPositionOffset(SizeCalculationContext sizeCalculationContext, DirectivePool styleDirectives, AbsoluteSize boxSize) {
		float[] positions = PositionOffsetCalculations.calculateOffset(sizeCalculationContext, styleDirectives);
		float[] margins = PositionOffsetCalculations.calculateMargin(sizeCalculationContext, styleDirectives);

		return PositionOffsetCalculations.calculateFixedPositionOffset(positions, margins, sizeCalculationContext.viewportSize(), boxSize);
	}

	public static PositionType getPositionType(DirectivePool styleDirectives) {
		return styleDirectives
			.getDirectiveOrEmpty(PositionTypeDirective.class)
			.map(PositionTypeDirective::getPositionType)
			.orElse(PositionType.STATIC);
	}

}
