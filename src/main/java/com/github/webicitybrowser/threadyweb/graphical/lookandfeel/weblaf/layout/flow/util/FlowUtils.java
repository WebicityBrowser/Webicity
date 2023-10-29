package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flow.LineHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebFontUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowUtils {
	
	private FlowUtils() {}

	public static float getLineHeight(LayoutManagerContext context, DirectivePool directives) {
		SizeCalculationContext sizeContext = LayoutSizeUtils.createSizeCalculationContext(context, true);
		SizeCalculation lineHeightSizeCalculation = directives
			.getDirectiveOrEmpty(LineHeightDirective.class)
			.map(LineHeightDirective::getLineHeightCalculation)
			.orElse(LineHeightDirective.NORMAL);
		return lineHeightSizeCalculation.calculate(sizeContext);
	}

	public static Font2D computeFont(LayoutManagerContext context, DirectivePool directives, FontMetrics parentFontMetrics) {
		return WebFontUtil.getFont(
			directives,
			LayoutSizeUtils.createSizeCalculationContext(context, parentFontMetrics, true),
			context.globalRenderContext());
	}

}
