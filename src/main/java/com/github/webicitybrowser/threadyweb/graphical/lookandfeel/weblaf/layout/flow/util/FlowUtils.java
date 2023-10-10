package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebFontUtil;

public final class FlowUtils {
	
	private FlowUtils() {}

	public static Font2D computeFont(LayoutManagerContext context, DirectivePool directives, FontMetrics parentFontMetrics) {
		return WebFontUtil.getFont(
			directives,
			LayoutSizeUtils.createSizeCalculationContext(context, parentFontMetrics, true),
			context.globalRenderContext());
	}

}
