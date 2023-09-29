package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.position;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionTypeDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.BoxPositioningOverride;
import com.github.webicitybrowser.threadyweb.graphical.value.PositionType;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class PositionOffsetUtil {
	
	private PositionOffsetUtil() {}

	public static BoxPositioningOverride getPositioningOverride(Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator, Box box) {
		float[] positions = PositionOffsetCalculations.calculateOffset(sizeCalculationContextGenerator, box);
		PositionType positionType = getPositionType(box);

		AbsolutePosition relativePositionOffset = PositionOffsetCalculations.calculateRelativePositionOffset(positions);

		return new BoxPositioningOverride(relativePositionOffset, positionType);
	}

	private static PositionType getPositionType(Box box) {
		return box
			.styleDirectives()
			.getDirectiveOrEmpty(PositionTypeDirective.class)
			.map(PositionTypeDirective::getPositionType)
			.orElse(PositionType.STATIC);
	}

}
