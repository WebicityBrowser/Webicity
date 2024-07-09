package com.github.webicitybrowser.threadyweb.graphical.layout.util;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.BottomBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.LeftBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.RightBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.TopBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class LayoutBorderWidthCalculations {

	public static float[] computeBorderWidths(SizeCalculationContext sizeCalculationContext, DirectivePool styleDirectives) {
		float[] borderWidth = new float[4];
		borderWidth[0] = computeBorderWidth(sizeCalculationContext, styleDirectives, LeftBorderWidthDirective.class, true);
		borderWidth[1] = computeBorderWidth(sizeCalculationContext, styleDirectives, RightBorderWidthDirective.class, true);
		borderWidth[2] = computeBorderWidth(sizeCalculationContext, styleDirectives, TopBorderWidthDirective.class, false);
		borderWidth[3] = computeBorderWidth(sizeCalculationContext, styleDirectives, BottomBorderWidthDirective.class, false);

		return borderWidth;
	}

	private static float computeBorderWidth(
		SizeCalculationContext sizeCalculationContext, DirectivePool styleDirectives, Class<?  extends BorderWidthDirective> directiveClass, boolean isHorizontal
	) {
		SizeCalculation sizeCalculation = styleDirectives
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(SizeCalculation.SIZE_ZERO);

		return sizeCalculation.calculate(sizeCalculationContext, isHorizontal);
	}
	
}
