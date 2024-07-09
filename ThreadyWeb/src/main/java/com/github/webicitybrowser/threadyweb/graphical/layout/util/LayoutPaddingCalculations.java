package com.github.webicitybrowser.threadyweb.graphical.layout.util;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.BottomPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.LeftPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.RightPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.TopPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class LayoutPaddingCalculations {

	public static float[] computePaddings(SizeCalculationContext sizeCalculationContext, DirectivePool styleDirectives) {
		float[] padding = new float[4];
		padding[0] = computePadding(sizeCalculationContext, styleDirectives, LeftPaddingDirective.class, true);
		padding[1] = computePadding(sizeCalculationContext, styleDirectives, RightPaddingDirective.class, true);
		padding[2] = computePadding(sizeCalculationContext, styleDirectives, TopPaddingDirective.class, false);
		padding[3] = computePadding(sizeCalculationContext, styleDirectives, BottomPaddingDirective.class, false);

		return padding;
	}

	private static float computePadding(
		SizeCalculationContext sizeCalculationContext, DirectivePool styleDirectives, Class<?  extends PaddingDirective> directiveClass, boolean isHorizontal
	) {
		SizeCalculation sizeCalculation = styleDirectives
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(SizeCalculation.SIZE_ZERO);

		return sizeCalculation.calculate(sizeCalculationContext, isHorizontal);
	}
	
}
