package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitContext;

public final class FlowBlockBlockRenderer {

	private FlowBlockBlockRenderer() {}

	public static void renderChild(FlowBlockRendererState state, Box childBox) {
		BoxOffsetDimensions boxDimensions = BoxOffsetDimensions.create(state, childBox);
		AbsoluteSize parentSize = state.getLocalRenderContext().preferredSize();
		FlowBlockUnitRenderingContext context = new FlowBlockUnitRenderingContext(
			state, childBox, boxDimensions,
			FlowBlockBlockRenderer::createChildLocalRenderContext,
			(childState, childSize) -> computeFallbackPreferredSize(parentSize, childSize, boxDimensions.margins())
		);
		
		FlowBlockPrerenderSizingInfo prerenderSizingInfo = FlowBlockUnitRenderer.prerenderChild(context);
		FlowBlockChildRenderResult childRenderResult = FlowBlockUnitRenderer.generateChildUnit(context, prerenderSizingInfo);
		AbsoluteSize finalChildSize = computeFinalChildSize(prerenderSizingInfo, childRenderResult);
		float[] finalMargins = computeFinalMargins(prerenderSizingInfo, finalChildSize);

		AbsolutePosition childPosition = state.positionTracker().addBox(finalChildSize, finalMargins);
		Rectangle childRect = new Rectangle(childPosition, finalChildSize);
		
		addChildToLayout(state, childBox, childRenderResult.unit(), childRect, boxDimensions);
	}

	private static void addChildToLayout(
		FlowBlockRendererState state, Box childBox, RenderedUnit childUnit, Rectangle childRect, BoxOffsetDimensions boxDimensions
	) {
		StyledUnitContext styledUnitContext = new StyledUnitContext(childBox, childUnit, childRect.size(), boxDimensions);
		RenderedUnit styledUnit = state.flowContext().styledUnitGenerator().generateStyledUnit(styledUnitContext);
		state.addChildLayoutResult(new ChildLayoutResult(styledUnit, childRect));
	}

	private static AbsoluteSize computeFinalChildSize(
		FlowBlockPrerenderSizingInfo prerenderSizingInfo, FlowBlockChildRenderResult childResult
	) {
		BoxOffsetDimensions boxDimensions = prerenderSizingInfo.sizingContext().boxOffsetDimensions();
		AbsoluteSize adjustedSize = LayoutSizeUtils.addPadding(childResult.adjustedSize(), boxDimensions.totalPadding());
		return stretchToParentSize(
			adjustedSize, prerenderSizingInfo.parentSize(),
			prerenderSizingInfo.forcedChildContentSize(), boxDimensions.margins());
	}

	private static float[] computeFinalMargins(FlowBlockPrerenderSizingInfo prerenderSizingInfo, AbsoluteSize adjustedSize) {
		float[] originalMargins =  prerenderSizingInfo.sizingContext().boxOffsetDimensions().margins();
		float[] adjustedMargins = FlowBlockMarginCalculations.expandAutoMargins(originalMargins, adjustedSize, prerenderSizingInfo.parentSize());
		return FlowBlockMarginCalculations.collapseOverflowMargins(prerenderSizingInfo.parentSize(), adjustedSize, adjustedMargins);
	}

	private static AbsoluteSize computeFallbackPreferredSize(AbsoluteSize parentSize, AbsoluteSize preferredSize, float[] margins) {
		float marginOffset = Math.max(0, margins[0]) + Math.max(0, margins[1]);
		float actualPreferredWidth = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			preferredSize.width() :
			parentSize.width() == RelativeDimension.UNBOUNDED ?
				RelativeDimension.UNBOUNDED :
				Math.max(0, parentSize.width() - marginOffset);
		float actualPreferredHeight = preferredSize.height();

		return new AbsoluteSize(actualPreferredWidth, actualPreferredHeight);
	}

	private static AbsoluteSize stretchToParentSize(
		AbsoluteSize adjustedSize, AbsoluteSize parentSize, AbsoluteSize preferredSize, float[] margins
	) {
		boolean canNotStretch =
			margins[0] == RelativeDimension.UNBOUNDED ||
			margins[1] == RelativeDimension.UNBOUNDED ||
			preferredSize.width() != RelativeDimension.UNBOUNDED ||
			parentSize.width() == RelativeDimension.UNBOUNDED;
		if (canNotStretch) return adjustedSize;

		float marginOffset = Math.max(0, margins[0]) + Math.max(0, margins[1]);
		
		float stretchedPreferredWidth = Math.max(parentSize.width() - marginOffset, adjustedSize.width());
		float stretchedPreferredHeight = adjustedSize.height();

		return new AbsoluteSize(stretchedPreferredWidth, stretchedPreferredHeight);
	}

	private static LocalRenderContext createChildLocalRenderContext(FlowBlockRendererState state, AbsoluteSize childSize) {
		FlowRootContextSwitch parentSwitch = state.flowContext().flowRootContextSwitch();
		AbsolutePosition predictedChildPosition = state.positionTracker().getPosition();
		AbsolutePosition offsetPredictedChildPosition = AbsoluteDimensionsMath.sum(
			predictedChildPosition, parentSwitch.predictedPosition(), AbsolutePosition::new);
		FlowRootContextSwitch childSwitch = new FlowRootContextSwitch(
			offsetPredictedChildPosition, parentSwitch.floatContext());

		return LocalRenderContext.create(childSize, new ContextSwitch[] { childSwitch });
	}

}
