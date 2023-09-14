package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutBorderWidthCalculations;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutPaddingCalculations;

public record FlowBlockRenderParameters(AbsoluteSize parentSize, float[] margins, float[] padding, float[] borders) {

	public float[] totalPadding() {
		float[] totalPadding = new float[4];
		for (int i = 0; i < 4; i++) {
			totalPadding[i] = padding[i] + borders[i];
		}
		return totalPadding;
	}

	public static FlowBlockRenderParameters create(FlowBlockRendererState state, Box childBox) {
		AbsoluteSize parentSize = state.getLocalRenderContext().getPreferredSize();
		float[] margins = FlowBlockMarginCalculations.computeMargins(state, childBox);
		float[] paddings = LayoutPaddingCalculations.computePaddings(
			state.flowContext().globalRenderContext(),
			state.flowContext().localRenderContext(),
			childBox);
		float[] borders = LayoutBorderWidthCalculations.computeBorderWidths(
			state.getGlobalRenderContext(),
			state.getLocalRenderContext(),
			childBox);

		return new FlowBlockRenderParameters(parentSize, margins, paddings, borders);
	}
	
}
