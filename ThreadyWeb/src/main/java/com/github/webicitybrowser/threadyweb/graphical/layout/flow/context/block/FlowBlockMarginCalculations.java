package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.BottomMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.LeftMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.RightMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.TopMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;


public final class FlowBlockMarginCalculations {

	private static float MARGIN_AUTO = RelativeDimension.UNBOUNDED;

	public static float[] computeMargins(FlowBlockRendererState state, DirectivePool styleDirectives) {
		float[] margins = new float[4];
		margins[0] = computeInitialMargin(state, styleDirectives, LeftMarginDirective.class, MARGIN_AUTO);
		margins[1] = computeInitialMargin(state, styleDirectives, RightMarginDirective.class, MARGIN_AUTO);
		margins[2] = computeInitialMargin(state, styleDirectives, TopMarginDirective.class, 0);
		margins[3] = computeInitialMargin(state, styleDirectives, BottomMarginDirective.class, 0);

		return margins;
	}

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
			adjustedMargins[1] = 0;
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

	private static float computeInitialMargin(
		FlowBlockRendererState state, DirectivePool styleDirectives, Class<?  extends MarginDirective> directiveClass, float defaultValue
	) {
		SizeCalculation sizeCalculation = styleDirectives
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(_1 -> 0);

		if (sizeCalculation == SizeCalculation.SIZE_AUTO) {
			return defaultValue;
		}

		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(
			state.flowContext().layoutManagerContext(), styleDirectives, true);
		return sizeCalculation.calculate(sizeCalculationContext);
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
