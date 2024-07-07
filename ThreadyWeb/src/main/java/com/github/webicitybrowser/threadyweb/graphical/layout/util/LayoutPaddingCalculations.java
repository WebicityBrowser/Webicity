package com.github.webicitybrowser.threadyweb.graphical.layout.util;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.BottomPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.LeftPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.RightPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.TopPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class LayoutPaddingCalculations {

	public static float[] computePaddings(SizeCalculationContext sizeCalculationContext, Box box) {
		float[] padding = new float[4];
		padding[0] = computePadding(sizeCalculationContext, box, LeftPaddingDirective.class, true);
		padding[1] = computePadding(sizeCalculationContext, box, RightPaddingDirective.class, true);
		padding[2] = computePadding(sizeCalculationContext, box, TopPaddingDirective.class, false);
		padding[3] = computePadding(sizeCalculationContext, box, BottomPaddingDirective.class, false);

		return padding;
	}

	private static float computePadding(
		SizeCalculationContext sizeCalculationContext, Box box, Class<?  extends PaddingDirective> directiveClass, boolean isHorizontal
	) {
		SizeCalculation sizeCalculation = box
			.styleDirectives()
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(SizeCalculation.SIZE_ZERO);

		return sizeCalculation.calculate(sizeCalculationContext, isHorizontal);
	}
	
}
