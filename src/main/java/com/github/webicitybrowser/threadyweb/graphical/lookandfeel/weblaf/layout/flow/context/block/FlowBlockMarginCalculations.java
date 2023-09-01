package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.MarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.MarginDirective.BottomMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.MarginDirective.LeftMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.MarginDirective.RightMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.MarginDirective.TopMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowBlockMarginCalculations {

	private static float MARGIN_AUTO = RelativeDimension.UNBOUNDED;

	public static float[] computeMargins(FlowBlockRendererState state, Box box) {
		float[] margins = new float[4];
		margins[0] = computeMargin(state, box, LeftMarginDirective.class, MARGIN_AUTO);
		margins[1] = computeMargin(state, box, RightMarginDirective.class, MARGIN_AUTO);
		margins[2] = computeMargin(state, box, TopMarginDirective.class, 0);
		margins[3] = computeMargin(state, box, BottomMarginDirective.class, 0);

		return margins;
	}

	public static float[] adjustMargins(FlowBlockRendererState state, float[] originalMargins, AbsoluteSize adjustedSize) {
		if (originalMargins[0] == MARGIN_AUTO && originalMargins[1] == MARGIN_AUTO) {
			return centerMargins(state, originalMargins, adjustedSize);
		}

		float[] adjustedMargins = new float[4];
		for (int i = 0; i < 4; i++) {
			adjustedMargins[i] = originalMargins[i];
		}
		adjustSingleMarginIfAuto(state, adjustedMargins, adjustedSize, 0);
		adjustSingleMarginIfAuto(state, adjustedMargins, adjustedSize, 1);

		float parentWidth = state.getLocalRenderContext().getPreferredSize().width();
		if (adjustedMargins[0] + adjustedMargins[1] + adjustedSize.width() > parentWidth) {
			adjustedMargins[1] = 0;
		}

		return adjustedMargins;
	}

	private static void adjustSingleMarginIfAuto(FlowBlockRendererState state, float[] margins, AbsoluteSize adjustedSize, int id) {
		float parentWidth = state.getLocalRenderContext().getPreferredSize().width();
		if (margins[id] == MARGIN_AUTO) {
			margins[id] = Math.max(0, parentWidth - margins[id == 1 ? 0 : 1] - adjustedSize.width());
		}
	}

	private static float computeMargin(
		FlowBlockRendererState state, Box box, Class<?  extends MarginDirective> directiveClass, float defaultValue
	) {
		SizeCalculation sizeCalculation = box
			.styleDirectives()
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getSizeCalculation())
			.orElse(_1 -> 0);

		if (sizeCalculation == SizeCalculation.SIZE_AUTO) {
			return defaultValue;
		}

		SizeCalculationContext sizeCalculationContext = createSizeCalculationContext(state);
		return sizeCalculation.calculate(sizeCalculationContext);
	}

	private static float[] centerMargins(FlowBlockRendererState state, float[] margins, AbsoluteSize adjustedSize) {
		float parentWidth = state.getLocalRenderContext().getPreferredSize().width();
		float leftMargin = (parentWidth - adjustedSize.width()) / 2;
		leftMargin = Math.max(leftMargin, 0);
		float rightMargin = leftMargin * 2 + adjustedSize.width() <= parentWidth
			? leftMargin
			: parentWidth - leftMargin - adjustedSize.width();
		return new float[] { leftMargin, rightMargin, margins[2], margins[3] };
	}

	private static SizeCalculationContext createSizeCalculationContext(FlowBlockRendererState state) {
		LocalRenderContext localRenderContext = state.getLocalRenderContext();
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		return new SizeCalculationContext(
			localRenderContext.getPreferredSize(),
			globalRenderContext.viewportSize(),
			localRenderContext.getParentFontMetrics(),
			globalRenderContext.rootFontMetrics(),
			true);
	}
	
}
