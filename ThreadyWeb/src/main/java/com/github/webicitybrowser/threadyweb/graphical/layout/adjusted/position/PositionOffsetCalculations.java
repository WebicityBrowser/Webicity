package com.github.webicitybrowser.threadyweb.graphical.layout.adjusted.position;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective.BottomPositionOffsetDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective.LeftPositionOffsetDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective.RightPositionOffsetDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective.TopPositionOffsetDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public class PositionOffsetCalculations {

	private static float POSITION_AUTO = RelativeDimension.UNBOUNDED;

	private PositionOffsetCalculations() {}

	public static float[] calculateOffset(SizeCalculationContext sizeCalculationContext, DirectivePool styleDirectives) {
		float[] offsets = new float[4];
		offsets[0] = computePosition(sizeCalculationContext, styleDirectives, LeftPositionOffsetDirective.class, true);
		offsets[1] = computePosition(sizeCalculationContext, styleDirectives, RightPositionOffsetDirective.class, true);
		offsets[2] = computePosition(sizeCalculationContext, styleDirectives, TopPositionOffsetDirective.class, false);
		offsets[3] = computePosition(sizeCalculationContext, styleDirectives, BottomPositionOffsetDirective.class, false);

		return offsets;
	}

	private static float computePosition(
		SizeCalculationContext sizeCalculationContext, DirectivePool styleDirectives,
		Class<? extends PositionOffsetDirective> directiveClass, boolean isHorizontal
	) {
		SizeCalculation sizeCalculation = styleDirectives
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(SizeCalculation.SIZE_AUTO);
		
		if (sizeCalculation == SizeCalculation.SIZE_AUTO) {
			return POSITION_AUTO;
		}

		return sizeCalculation.calculate(sizeCalculationContext, isHorizontal);
	}

	public static AbsolutePosition calculateRelativePositionOffset(float[] positions) {
		float xOffset = positions[0] == POSITION_AUTO ?
			positions[1] == POSITION_AUTO ? 0 : -positions[1] :
			positions[0];
		float yOffset = positions[2] == POSITION_AUTO ?
			positions[3] == POSITION_AUTO ? 0 : -positions[3] :
			positions[2];
		
		return new AbsolutePosition(xOffset, yOffset);
	}

}
