package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;

public final class FlexUtils {
	
	private FlexUtils() {}

	public static LocalRenderContext createChildRenderContext(FlexItem flexItem, AbsoluteSize preferredSize, LocalRenderContext localRenderContext) {
		return LocalRenderContext.create(
			preferredSize,
			localRenderContext.getParentFontMetrics(),
			new ContextSwitch[0]);
	}

}
