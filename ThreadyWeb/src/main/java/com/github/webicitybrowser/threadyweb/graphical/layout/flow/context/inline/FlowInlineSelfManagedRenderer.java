package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutBorderWidthCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutPaddingCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils.LayoutSizingContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitContext;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowInlineSelfManagedRenderer {
	
	private FlowInlineSelfManagedRenderer() {}

	public static void addSelfManagedBoxToLine(FlowInlineRenderContext state, Box childBox) {
		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(
			state.flowContext().layoutRenderContext(), childBox.styleDirectives());
		BoxOffsetDimensions boxOffsetDimensions = getBoxOffsetDimensions(childBox, sizeCalculationContext);
		AbsoluteSize preferredSize = computePreferredSize(sizeCalculationContext, childBox, boxOffsetDimensions);
		AbsoluteSize containerSize = new AbsoluteSize(state.getLocalRenderContext().preferredSize().width(), RelativeDimension.UNBOUNDED);
		AbsoluteSize contentSize = LayoutSizeUtils.subtractPadding(preferredSize, boxOffsetDimensions.padding());
		RenderedUnit childUnit = renderChildUnit(state, childBox, contentSize);
		if (childUnit.fitSize().width() > containerSize.width() && preferredSize.width() == RelativeDimension.UNBOUNDED) {
			float[] padding = boxOffsetDimensions.padding();
			AbsoluteSize adjustedContentSize = new AbsoluteSize(
				containerSize.width() - padding[0] - padding[1], contentSize.height());
			childUnit = renderChildUnit(state, childBox, adjustedContentSize);
		}
		AbsoluteSize rawChildSize = childUnit.fitSize();
		AbsoluteSize outerSize = LayoutSizeUtils.addPadding(rawChildSize, boxOffsetDimensions.padding());
		AbsoluteSize adjustedOuterSize = LayoutSizeUtils.enforceSize(outerSize, preferredSize);

		StyledUnitContext styledUnitContext = new StyledUnitContext(childBox, childUnit, adjustedOuterSize, boxOffsetDimensions);
		RenderedUnit styledUnit = state.flowConfig().styledUnitGenerator().generateStyledUnit(styledUnitContext);

		FlowInlineRendererUtil.startNewLineIfNotFits(state, adjustedOuterSize);
		state.lineContext().currentLine().add(styledUnit, adjustedOuterSize);
	}

	private static BoxOffsetDimensions getBoxOffsetDimensions(Box childBox, SizeCalculationContext sizeCalculationContext) {
		float[] padding = LayoutPaddingCalculations.computePaddings(sizeCalculationContext, childBox);
		float[] borders = LayoutBorderWidthCalculations.computeBorderWidths(sizeCalculationContext, childBox);
		return new BoxOffsetDimensions(new float[4], padding, borders);
	}

	private static AbsoluteSize computePreferredSize(
		SizeCalculationContext sizeCalculationContext, Box childBox, BoxOffsetDimensions boxDimensions
	) {
		LayoutSizingContext layoutSizingContext = LayoutSizeUtils.createLayoutSizingContext(
			childBox.styleDirectives(), sizeCalculationContext, boxDimensions
		);
		return LayoutSizeUtils.computePreferredSize(childBox.styleDirectives(), layoutSizingContext);
	}

	private static RenderedUnit renderChildUnit(FlowInlineRenderContext state, Box childBox, AbsoluteSize contentSize) {
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		LocalRenderContext childLocalRenderContext = new LocalRenderContext(contentSize, new ContextSwitch[0]);
		return UIPipeline.render(childBox, globalRenderContext, childLocalRenderContext);
	}

}
