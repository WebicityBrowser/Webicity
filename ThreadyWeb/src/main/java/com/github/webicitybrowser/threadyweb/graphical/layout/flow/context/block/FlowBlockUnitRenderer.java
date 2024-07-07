package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.FlowSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils.LayoutSizingContext;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowBlockUnitRenderer {
	
	private FlowBlockUnitRenderer() {}

	public static FlowBlockPrerenderSizingInfo prerenderChild(FlowBlockUnitRenderingContext context) {
		FlowBlockRendererState state = context.state();
		AbsoluteSize parentSize = state.getLocalRenderContext().preferredSize();
		Box childBox = context.childBox();
		BoxOffsetDimensions renderParameters = context.renderParameters();

		LayoutSizingContext layoutSizingContext = createLayoutSizingContext(state, childBox, renderParameters);
		AbsoluteSize forcedChildOuterSize = computePreferredSize(childBox, layoutSizingContext);
		AbsoluteSize preferredChildOuterSize = context.childSizeGenerator().apply(state, forcedChildOuterSize);

		AbsoluteSize forcedChildContentSize = LayoutSizeUtils.subtractPadding(forcedChildOuterSize, renderParameters.totalPadding());
		AbsoluteSize preferredChildContentSize = LayoutSizeUtils.subtractPadding(preferredChildOuterSize, renderParameters.totalPadding());

		return new FlowBlockPrerenderSizingInfo(forcedChildContentSize, preferredChildContentSize, parentSize, layoutSizingContext);
	}

	public static FlowBlockChildRenderResult generateChildUnit(FlowBlockUnitRenderingContext context, FlowBlockPrerenderSizingInfo prerenderSizingInfo) {
		// If the size is determinate, we just use that size (but clipped to constraints)
		// If not, take the fit size and clip it to constraints
		Box childBox = context.childBox();
		AbsoluteSize precomputedSize = prerenderSizingInfo.preferredChildContentSize();

		precomputedSize = FlowBlockSizeCalculations.clipHorizontalSize(childBox.styleDirectives(), precomputedSize, prerenderSizingInfo);
		FlowBlockPrerenderSizingInfo adjustedPrerenderSizingInfo = new FlowBlockPrerenderSizingInfo(
			prerenderSizingInfo.forcedChildContentSize(), precomputedSize, prerenderSizingInfo.parentSize(), prerenderSizingInfo.sizingContext()
		);

		if (precomputedSize.height() == RelativeDimension.UNBOUNDED) {
			AbsoluteSize fitSize = context.state().getGlobalRenderContext().renderCache().cachedRender(
				childBox, context.state().getGlobalRenderContext(), new LocalRenderContext(precomputedSize, new ContextSwitch[0])
			).fitSize();
			precomputedSize = new AbsoluteSize(precomputedSize.width(), fitSize.height());
		}
		precomputedSize = FlowSizeUtils.enforcePreferredSize(precomputedSize, prerenderSizingInfo.forcedChildContentSize());
		precomputedSize = FlowBlockSizeCalculations.clipContentSize(childBox.styleDirectives(), precomputedSize, prerenderSizingInfo);

		adjustedPrerenderSizingInfo = new FlowBlockPrerenderSizingInfo(
			prerenderSizingInfo.forcedChildContentSize(), precomputedSize, prerenderSizingInfo.parentSize(), prerenderSizingInfo.sizingContext()
		);
		
		RenderedUnit childUnit = renderChildUnit(context, adjustedPrerenderSizingInfo);
		AbsoluteSize adjustedSize = FlowSizeUtils.enforcePreferredSize(childUnit.fitSize(), prerenderSizingInfo.forcedChildContentSize());
		AbsoluteSize clippedAdjustedSize = FlowBlockSizeCalculations.clipContentSize(childBox.styleDirectives(), adjustedSize, prerenderSizingInfo);

		return new FlowBlockChildRenderResult(childUnit, clippedAdjustedSize);
	}

	private static LayoutSizingContext createLayoutSizingContext(FlowBlockRendererState state, Box childBox, BoxOffsetDimensions boxOffsetDimensions) {
		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(
			state.flowContext().layoutRenderContext(), childBox.styleDirectives());

		return LayoutSizeUtils.createLayoutSizingContext(
			childBox.styleDirectives(), sizeCalculationContext, boxOffsetDimensions
		);
}

	private static AbsoluteSize computePreferredSize(Box childBox, LayoutSizingContext layoutSizingContext) {
		return LayoutSizeUtils.computePreferredSize(childBox.styleDirectives(), layoutSizingContext);
	}

	private static RenderedUnit renderChildUnit(FlowBlockUnitRenderingContext context, FlowBlockPrerenderSizingInfo prerenderSizingInfo) {
		FlowBlockRendererState state = context.state();
		Box childBox = context.childBox();
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		LocalRenderContext childLocalRenderContext = context.localRenderContextGenerator().apply(
			state,
			context.childSizeGenerator().apply(state, prerenderSizingInfo.preferredChildContentSize()));
		return UIPipeline.render(childBox, globalRenderContext, childLocalRenderContext);
	}

}
