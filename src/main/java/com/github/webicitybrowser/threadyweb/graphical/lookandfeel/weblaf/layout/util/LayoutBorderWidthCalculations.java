package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.BottomBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.LeftBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.RightBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.TopBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class LayoutBorderWidthCalculations {

	public static float[] computeBorderWidths(GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, Box box) {
		float[] borderWidth = new float[4];
		borderWidth[0] = computeBorderWidth(globalRenderContext, localRenderContext, box, LeftBorderWidthDirective.class);
		borderWidth[1] = computeBorderWidth(globalRenderContext, localRenderContext, box, RightBorderWidthDirective.class);
		borderWidth[2] = computeBorderWidth(globalRenderContext, localRenderContext, box, TopBorderWidthDirective.class);
		borderWidth[3] = computeBorderWidth(globalRenderContext, localRenderContext, box, BottomBorderWidthDirective.class);

		return borderWidth;
	}

	private static float computeBorderWidth(
		GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, Box box, Class<?  extends BorderWidthDirective> directiveClass
	) {
		SizeCalculation sizeCalculation = box
			.styleDirectives()
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(_1 -> 0);

		SizeCalculationContext sizeCalculationContext = createSizeCalculationContext(globalRenderContext, localRenderContext);
		return sizeCalculation.calculate(sizeCalculationContext);
	}

	private static SizeCalculationContext createSizeCalculationContext(GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return new SizeCalculationContext(
			localRenderContext.getPreferredSize(),
			globalRenderContext.viewportSize(),
			localRenderContext.getParentFontMetrics(),
			globalRenderContext.rootFontMetrics(),
			true);
	}
	
}
