package com.github.webicitybrowser.threadyweb.graphical.layout.util;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.BottomBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.LeftBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.RightBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.TopBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class LayoutBorderWidthCalculations {

	public static float[] computeBorderWidths(SizeCalculationContext sizeCalculationContext, Box box) {
		float[] borderWidth = new float[4];
		borderWidth[0] = computeBorderWidth(sizeCalculationContext, box, LeftBorderWidthDirective.class);
		borderWidth[1] = computeBorderWidth(sizeCalculationContext, box, RightBorderWidthDirective.class);
		borderWidth[2] = computeBorderWidth(sizeCalculationContext, box, TopBorderWidthDirective.class);
		borderWidth[3] = computeBorderWidth(sizeCalculationContext, box, BottomBorderWidthDirective.class);

		return borderWidth;
	}

	private static float computeBorderWidth(
		SizeCalculationContext sizeCalculationContext, Box box, Class<?  extends BorderWidthDirective> directiveClass
	) {
		SizeCalculation sizeCalculation = box
			.styleDirectives()
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(_1 -> 0);

		return sizeCalculation.calculate(sizeCalculationContext);
	}
	
}
