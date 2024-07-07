package com.github.webicitybrowser.threadyweb.graphical.layout.util;

import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.BottomMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.LeftMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.RightMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.TopMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class LayoutMarginCalculations {

	public static float MARGIN_AUTO = RelativeDimension.UNBOUNDED;
	
	private LayoutMarginCalculations() {}

	public static float[] computeMargins(SizeCalculationContext sizeCalculationContext, DirectivePool styleDirectives) {
		float[] margins = new float[4];
		margins[0] = computeMargin(sizeCalculationContext, styleDirectives, LeftMarginDirective.class, true);
		margins[1] = computeMargin(sizeCalculationContext, styleDirectives, RightMarginDirective.class, true);
		margins[2] = computeMargin(sizeCalculationContext, styleDirectives, TopMarginDirective.class, false);
		margins[3] = computeMargin(sizeCalculationContext, styleDirectives, BottomMarginDirective.class, false);

		return margins;
	}

	public static float[] zeroAutoMargins(float[] margins) {
		float[] zeroed = new float[4];
		for (int i = 0; i < 4; i++) {
			zeroed[i] = margins[i] == MARGIN_AUTO ? 0 : margins[i];
		}
		return zeroed;
	}

	private static float computeMargin(
		SizeCalculationContext sizeCalculationContext, DirectivePool styleDirectives, Class<?  extends MarginDirective> directiveClass, boolean isHorizontal
	) {
		SizeCalculation sizeCalculation = styleDirectives
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(SizeCalculation.SIZE_ZERO);

		if (sizeCalculation == SizeCalculation.SIZE_AUTO) {
			return MARGIN_AUTO;
		}

		return sizeCalculation.calculate(sizeCalculationContext, isHorizontal);
	}

}
