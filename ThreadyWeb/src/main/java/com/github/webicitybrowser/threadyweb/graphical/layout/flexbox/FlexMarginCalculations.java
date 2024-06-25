package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox;

import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.BottomMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.LeftMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.RightMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.TopMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlexMarginCalculations {
	
	private FlexMarginCalculations() {}

	private static float MARGIN_AUTO = RelativeDimension.UNBOUNDED;

	public static float[] computeMargins(GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, Box box) {
		float[] margins = new float[4];
		margins[0] = computeMargin(globalRenderContext, localRenderContext, box, LeftMarginDirective.class, MARGIN_AUTO);
		margins[1] = computeMargin(globalRenderContext, localRenderContext, box, RightMarginDirective.class, MARGIN_AUTO);
		margins[2] = computeMargin(globalRenderContext, localRenderContext, box, TopMarginDirective.class, MARGIN_AUTO);
		margins[3] = computeMargin(globalRenderContext, localRenderContext, box, BottomMarginDirective.class, MARGIN_AUTO);

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
		GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext,
		Box box, Class<?  extends MarginDirective> directiveClass, float defaultValue
	) {
		DirectivePool directives = box.styleDirectives();
		SizeCalculation sizeCalculation = directives
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(_1 -> 0);

		if (sizeCalculation == SizeCalculation.SIZE_AUTO) {
			return defaultValue;
		}

		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(globalRenderContext, localRenderContext, directives, true);
		return sizeCalculation.calculate(sizeCalculationContext);
	}

	
	
}
