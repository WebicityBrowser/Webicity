package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils.LayoutSizingContext;

public record FlowBlockPrerenderSizingInfo(
	AbsoluteSize enforcedChildSize, AbsoluteSize precomputedChildSize, LayoutSizingContext sizingContext
) {

	public float[] totalPadding() {
		return sizingContext.padding();
	}

}
