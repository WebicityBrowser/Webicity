package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebFontUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowUtils {
	
	private FlowUtils() {}

	public static SizeCalculationContext createSizeCalculationContext(FlowRenderContext context, FontMetrics parentFontMetrics, boolean isHorizontal) {
		return new SizeCalculationContext(
			context.localRenderContext().getPreferredSize(),
			context.globalRenderContext().viewportSize(),
			parentFontMetrics,
			context.globalRenderContext().rootFontMetrics(),
			isHorizontal);
	}

	public static Font2D computeFont(FlowRenderContext context, DirectivePool directives, FontMetrics parentFontMetrics) {
		return WebFontUtil.getFont(
			directives,
			createSizeCalculationContext(context, parentFontMetrics, true),
			context.globalRenderContext());
	}

}
