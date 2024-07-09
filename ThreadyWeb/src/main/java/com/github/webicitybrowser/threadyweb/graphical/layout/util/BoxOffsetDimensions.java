package com.github.webicitybrowser.threadyweb.graphical.layout.util;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public record BoxOffsetDimensions(float[] margins, float[] padding, float[] borders) {

	public float[] totalPadding() {
		float[] totalPadding = new float[4];
		for (int i = 0; i < 4; i++) {
			totalPadding[i] = padding[i] + borders[i];
		}
		return totalPadding;
	}

	public static BoxOffsetDimensions create(LayoutRenderContext layoutRenderContext, DirectivePool styleDirectives) {
		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(
			layoutRenderContext, styleDirectives);
		float[] margins = LayoutMarginCalculations.computeMargins(sizeCalculationContext, styleDirectives);
		float[] paddings = LayoutPaddingCalculations.computePaddings(sizeCalculationContext, styleDirectives);
		float[] borders = LayoutBorderWidthCalculations.computeBorderWidths(sizeCalculationContext, styleDirectives);

		return new BoxOffsetDimensions(margins, paddings, borders);
	}
	
}
