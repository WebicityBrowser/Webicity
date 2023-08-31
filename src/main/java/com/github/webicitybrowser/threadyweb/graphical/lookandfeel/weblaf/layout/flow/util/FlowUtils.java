package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebFontUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowUtils {
	
	private FlowUtils() {}

	public static Font2D computeFont(FlowRenderContext context, FontMetrics parentFontMetrics) {
		SizeCalculationContext sizeCalculationContext = new SizeCalculationContext(
			context.localRenderContext().getPreferredSize(),
			context.globalRenderContext().getViewportSize(),
			parentFontMetrics,
			false
		);
		return WebFontUtil.getFont(
			context.box().styleDirectives(),
			sizeCalculationContext,
			context.globalRenderContext());
	}

}
