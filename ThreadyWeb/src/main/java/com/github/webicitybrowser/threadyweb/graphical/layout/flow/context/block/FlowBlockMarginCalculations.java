package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutMarginCalculations;


public final class FlowBlockMarginCalculations {

	private static final float MARGIN_AUTO = LayoutMarginCalculations.MARGIN_AUTO;

	public static float[] expandAutoMargins(float[] originalMargins, AbsoluteSize adjustedSize, AbsoluteSize parentSize) {
		if (originalMargins[0] == MARGIN_AUTO && originalMargins[1] == MARGIN_AUTO) {
			return centerMargins(parentSize, originalMargins, adjustedSize);
		}

		float[] adjustedMargins = new float[4];
		for (int i = 0; i < 4; i++) {
			adjustedMargins[i] = originalMargins[i];
		}
		computeSingleAutoMargin(parentSize, adjustedMargins, adjustedSize, 0);
		computeSingleAutoMargin(parentSize, adjustedMargins, adjustedSize, 1);

		float parentWidth = parentSize.width();
		float widthWithMargins = adjustedSize.width() + adjustedMargins[0] + adjustedMargins[1];
		if (parentWidth != RelativeDimension.UNBOUNDED && widthWithMargins > parentWidth) {
			adjustedMargins[1] = parentWidth = widthWithMargins;
			//adjustedMargins[1] = 0;
		}

		return adjustedMargins;
	}

	public static float[] collapseOverflowMargins(AbsoluteSize parentSize, AbsoluteSize childSize, float[] originalMargins) {
		if (parentSize.width() == RelativeDimension.UNBOUNDED) {
			return originalMargins;
		}
		float[] adjustedMargins = new float[] {
			originalMargins[0], originalMargins[1], originalMargins[2], originalMargins[3]
		};
		if (childSize.width() + originalMargins[0] + originalMargins[1] != parentSize.width()) {
			adjustedMargins[1] = Math.max(0, parentSize.width() - childSize.width() - originalMargins[0]);
		}

		return adjustedMargins;
	 }

	private static void computeSingleAutoMargin(AbsoluteSize parentSize, float[] margins, AbsoluteSize adjustedSize, int id) {
		float parentWidth = parentSize.width();
		if (margins[id] == MARGIN_AUTO) {
			margins[id] = Math.max(0, parentWidth - margins[id == 1 ? 0 : 1] - adjustedSize.width());
		}
	}

	private static float[] centerMargins(AbsoluteSize parentSize, float[] margins, AbsoluteSize adjustedSize) {
		float parentWidth = parentSize.width();
		float leftMargin = (parentWidth - adjustedSize.width()) / 2;
		leftMargin = Math.max(leftMargin, 0);
		float rightMargin = leftMargin * 2 + adjustedSize.width() <= parentWidth
			? leftMargin
			: parentWidth - leftMargin - adjustedSize.width();
		return new float[] { leftMargin, rightMargin, margins[2], margins[3] };
	}
	
}
