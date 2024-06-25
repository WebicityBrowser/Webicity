package com.github.webicitybrowser.threadyweb.graphical.layout.adjusted.position;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionTypeDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.PositionType;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class PositionOffsetUtil {
	
	private PositionOffsetUtil() {}

	public static AbsolutePosition getRelativePositionOffset(
		Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator, DirectivePool styleDirectives
	) {
		float[] positions = PositionOffsetCalculations.calculateOffset(sizeCalculationContextGenerator, styleDirectives);

		return PositionOffsetCalculations.calculateRelativePositionOffset(positions);
	}

	public static PositionType getPositionType(DirectivePool styleDirectives) {
		return styleDirectives
			.getDirectiveOrEmpty(PositionTypeDirective.class)
			.map(PositionTypeDirective::getPositionType)
			.orElse(PositionType.STATIC);
	}

}
