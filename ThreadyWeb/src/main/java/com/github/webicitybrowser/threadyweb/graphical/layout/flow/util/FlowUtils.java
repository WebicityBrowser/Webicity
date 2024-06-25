package com.github.webicitybrowser.threadyweb.graphical.layout.flow.util;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flow.LineHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowUtils {
	
	private FlowUtils() {}

	public static float getLineHeight(LayoutManagerContext context, DirectivePool directives) {
		SizeCalculationContext sizeContext = LayoutSizeUtils.createSizeCalculationContext(context, directives, true);
		SizeCalculation lineHeightSizeCalculation = directives
			.getDirectiveOrEmpty(LineHeightDirective.class)
			.map(LineHeightDirective::getLineHeightCalculation)
			.orElse(LineHeightDirective.NORMAL);
		return lineHeightSizeCalculation.calculate(sizeContext);
	}

}
