package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.BottomPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.LeftPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.RightPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.TopPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowPaddingCalculations {

	public static float[] computePaddings(FlowRenderContext context, Box box) {
		float[] padding = new float[4];
		padding[0] = computePadding(context, box, LeftPaddingDirective.class);
		padding[1] = computePadding(context, box, RightPaddingDirective.class);
		padding[2] = computePadding(context, box, TopPaddingDirective.class);
		padding[3] = computePadding(context, box, BottomPaddingDirective.class);

		return padding;
	}

	private static float computePadding(
		FlowRenderContext context, Box box, Class<?  extends PaddingDirective> directiveClass
	) {
		SizeCalculation sizeCalculation = box
			.styleDirectives()
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(_1 -> 0);

		SizeCalculationContext sizeCalculationContext = createSizeCalculationContext(context);
		return sizeCalculation.calculate(sizeCalculationContext);
	}

	private static SizeCalculationContext createSizeCalculationContext(FlowRenderContext context) {
		LocalRenderContext localRenderContext = context.localRenderContext();
		GlobalRenderContext globalRenderContext = context.globalRenderContext();
		return new SizeCalculationContext(
			localRenderContext.getPreferredSize(),
			globalRenderContext.viewportSize(),
			localRenderContext.getParentFontMetrics(),
			globalRenderContext.rootFontMetrics(),
			true);
	}
	
}
