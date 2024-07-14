package com.github.webicitybrowser.threadyweb.graphical.layout.util;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public record BoxOffsetDimensions(float[] margins, float[] padding, float[] borders) {

	public float[] totalPadding() {
		float[] totalPadding = new float[4];
		for (int i = 0; i < 4; i++) {
			totalPadding[i] = padding[i] + borders[i];
		}
		return totalPadding;
	}

	public static BoxOffsetDimensions create(LayoutRenderContext layoutRenderContext, ComponentUI componentUI) {
		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(
			layoutRenderContext, componentUI);
		float[] margins = LayoutMarginCalculations.computeMargins(sizeCalculationContext, componentUI.styleDirectives());
		float[] paddings = LayoutPaddingCalculations.computePaddings(sizeCalculationContext, componentUI.styleDirectives());
		float[] borders = LayoutBorderWidthCalculations.computeBorderWidths(sizeCalculationContext, componentUI.styleDirectives());

		return new BoxOffsetDimensions(margins, paddings, borders);
	}

	@Override
	public String toString() {
		String marginsFormatted = String.format("[%f, %f, %f, %f]", margins[0], margins[1], margins[2], margins[3]);
		String paddingsFormatted = String.format("[%f, %f, %f, %f]", padding[0], padding[1], padding[2], padding[3]);
		String bordersFormatted = String.format("[%f, %f, %f, %f]", borders[0], borders[1], borders[2], borders[3]);
		return "BoxOffsetDimensions[" +
			"margins=" + marginsFormatted +
			", padding=" + paddingsFormatted +
			", borders=" + bordersFormatted +
			']';
	}
	
}
