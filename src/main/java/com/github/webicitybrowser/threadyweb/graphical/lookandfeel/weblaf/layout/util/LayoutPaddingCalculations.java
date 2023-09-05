package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.BottomPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.LeftPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.RightPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.TopPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class LayoutPaddingCalculations {

	public static float[] computePaddings(GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, Box box) {
		float[] padding = new float[4];
		padding[0] = computePadding(globalRenderContext, localRenderContext, box, LeftPaddingDirective.class);
		padding[1] = computePadding(globalRenderContext, localRenderContext, box, RightPaddingDirective.class);
		padding[2] = computePadding(globalRenderContext, localRenderContext, box, TopPaddingDirective.class);
		padding[3] = computePadding(globalRenderContext, localRenderContext, box, BottomPaddingDirective.class);

		return padding;
	}

	private static float computePadding(
		GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, Box box, Class<?  extends PaddingDirective> directiveClass
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
