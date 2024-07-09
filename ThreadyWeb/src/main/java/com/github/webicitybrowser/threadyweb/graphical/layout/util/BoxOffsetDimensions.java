package com.github.webicitybrowser.threadyweb.graphical.layout.util;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public record BoxOffsetDimensions(float[] margins, float[] padding, float[] borders) {

	public float[] totalPadding() {
		float[] totalPadding = new float[4];
		for (int i = 0; i < 4; i++) {
			totalPadding[i] = padding[i] + borders[i];
		}
		return totalPadding;
	}

	public static BoxOffsetDimensions create(FlowBlockRenderContext state, Box childBox) {
		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(
			state.flowContext().layoutRenderContext(), childBox.styleDirectives());
		float[] margins = LayoutMarginCalculations.computeMargins(sizeCalculationContext, childBox.styleDirectives());
		float[] paddings = LayoutPaddingCalculations.computePaddings(sizeCalculationContext, childBox);
		float[] borders = LayoutBorderWidthCalculations.computeBorderWidths(sizeCalculationContext, childBox);

		return new BoxOffsetDimensions(margins, paddings, borders);
	}
	
}
