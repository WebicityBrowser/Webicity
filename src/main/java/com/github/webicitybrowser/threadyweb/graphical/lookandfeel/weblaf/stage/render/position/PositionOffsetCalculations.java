package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.position;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionDirective.BottomPositionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionDirective.LeftPositionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionDirective.RightPositionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionDirective.TopPositionDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public class PositionOffsetCalculations {

	private static float POSITION_AUTO = RelativeDimension.UNBOUNDED;

	private PositionOffsetCalculations() {}

	public static float[] calculateOffset(Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator, Box box) {
		float[] margins = new float[4];
		SizeCalculationContext horizontalSizeCalculationContext = sizeCalculationContextGenerator.apply(true);
		SizeCalculationContext verticalSizeCalculationContext = sizeCalculationContextGenerator.apply(false);
		margins[0] = computePosition(horizontalSizeCalculationContext, box, LeftPositionDirective.class, POSITION_AUTO);
		margins[1] = computePosition(horizontalSizeCalculationContext, box, RightPositionDirective.class, POSITION_AUTO);
		margins[2] = computePosition(verticalSizeCalculationContext, box, TopPositionDirective.class, POSITION_AUTO);
		margins[3] = computePosition(verticalSizeCalculationContext, box, BottomPositionDirective.class, POSITION_AUTO);

		return margins;
	}

	private static float computePosition(
		SizeCalculationContext sizeCalculationContext,
		Box box, Class<? extends PositionDirective> directiveClass, float defaultValue
	) {
		SizeCalculation sizeCalculation = box
			.styleDirectives()
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(_1 -> 0);
		
		if (sizeCalculation == SizeCalculation.SIZE_AUTO) {
			return defaultValue;
		}

		return sizeCalculation.calculate(sizeCalculationContext);
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
