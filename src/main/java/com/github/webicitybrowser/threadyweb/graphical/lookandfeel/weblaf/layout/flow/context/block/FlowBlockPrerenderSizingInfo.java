package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils.LayoutSizingContext;

public record FlowBlockPrerenderSizingInfo(
	AbsoluteSize enforcedChildSize, AbsoluteSize precomputedChildSize, LayoutSizingContext sizingContext
) {

	public float[] totalPadding() {
		float[] totalPadding = new float[4];
		float[] padding = padding();
		float[] borders = borders();
		for (int i = 0; i < 4; i++) {
			totalPadding[i] = padding[i] + borders[i];
		}

		return totalPadding;
	}

	public float[] padding() {
		return sizingContext.padding();
	}

	public float[] borders() {
		return sizingContext.borders();
	}

}
