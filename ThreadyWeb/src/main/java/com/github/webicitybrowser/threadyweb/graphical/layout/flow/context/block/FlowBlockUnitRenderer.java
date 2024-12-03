package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils.LayoutSizingContext;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowBlockUnitRenderer {
	
	private FlowBlockUnitRenderer() {}

	public static FlowBlockPrerenderSizingInfo prerenderChild(LayoutRenderContext layoutRenderContext, FlowBlockUnitRenderingContext context) {
		AbsoluteSize parentSize = layoutRenderContext.localRenderContext().preferredSize();
		Box childBox = context.childBox();
		BoxOffsetDimensions renderParameters = context.renderParameters();

		LayoutSizingContext layoutSizingContext = createLayoutSizingContext(layoutRenderContext, childBox, renderParameters);
		AbsoluteSize forcedChildOuterSize = computePreferredSize(childBox, layoutSizingContext);
		AbsoluteSize preferredChildOuterSize = context.childSizeGenerator().apply(forcedChildOuterSize);

		AbsoluteSize forcedChildContentSize = LayoutSizeUtils.subtractPadding(forcedChildOuterSize, renderParameters.totalPadding());
		AbsoluteSize preferredChildContentSize = LayoutSizeUtils.subtractPadding(preferredChildOuterSize, renderParameters.totalPadding());

		return new FlowBlockPrerenderSizingInfo(forcedChildContentSize, preferredChildContentSize, parentSize, layoutSizingContext);
	}

	public static FlowBlockChildRenderResult generateChildUnit(
		FlowBlockUnitRenderingContext context, FlowBlockPrerenderSizingInfo prerenderSizingInfo, GlobalRenderContext globalRenderContext
	) {
		// If the size is determinate, we just use that size (but clipped to constraints)
		// If not, take the fit size and clip it to constraints
		Box childBox = context.childBox();
		AbsoluteSize precomputedSize = prerenderSizingInfo.preferredChildContentSize();

		precomputedSize = FlowBlockSizeCalculations.clipHorizontalSize(childBox.styleDirectives(), precomputedSize, prerenderSizingInfo);
		FlowBlockPrerenderSizingInfo adjustedPrerenderSizingInfo = new FlowBlockPrerenderSizingInfo(
			prerenderSizingInfo.forcedChildContentSize(), precomputedSize, prerenderSizingInfo.parentSize(), prerenderSizingInfo.sizingContext()
		);

		if (
			precomputedSize.height() == RelativeDimension.UNBOUNDED
			&& FlowBlockSizeCalculations.wouldClipHeight(childBox.styleDirectives(), adjustedPrerenderSizingInfo.sizingContext())
		) {
			AbsoluteSize fitSize = globalRenderContext.renderCache().cachedRender(
				childBox, globalRenderContext, new LocalRenderContext(precomputedSize, new ContextSwitch[0])
			).fitSize();
			precomputedSize = new AbsoluteSize(precomputedSize.width(), fitSize.height());
		}
		precomputedSize = LayoutSizeUtils.enforceSize(precomputedSize, prerenderSizingInfo.forcedChildContentSize());
		precomputedSize = FlowBlockSizeCalculations.clipContentSize(childBox.styleDirectives(), precomputedSize, prerenderSizingInfo);

		adjustedPrerenderSizingInfo = new FlowBlockPrerenderSizingInfo(
			prerenderSizingInfo.forcedChildContentSize(), precomputedSize, prerenderSizingInfo.parentSize(), prerenderSizingInfo.sizingContext()
		);
		
		RenderedUnit childUnit = renderChildUnit(context, adjustedPrerenderSizingInfo, globalRenderContext);
		AbsoluteSize adjustedSize = LayoutSizeUtils.enforceSize(childUnit.fitSize(), prerenderSizingInfo.forcedChildContentSize());
		AbsoluteSize clippedAdjustedSize = FlowBlockSizeCalculations.clipContentSize(childBox.styleDirectives(), adjustedSize, prerenderSizingInfo);

		return new FlowBlockChildRenderResult(childUnit, clippedAdjustedSize);
	}

	private static LayoutSizingContext createLayoutSizingContext(LayoutRenderContext layoutRenderContext, Box childBox, BoxOffsetDimensions boxOffsetDimensions) {
		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(layoutRenderContext, childBox.styleDirectives());
		return LayoutSizeUtils.createLayoutSizingContext(childBox.styleDirectives(), sizeCalculationContext, boxOffsetDimensions);
}

	private static AbsoluteSize computePreferredSize(Box childBox, LayoutSizingContext layoutSizingContext) {
		return LayoutSizeUtils.computePreferredSize(childBox.styleDirectives(), layoutSizingContext);
	}

	private static RenderedUnit renderChildUnit(
		FlowBlockUnitRenderingContext context, FlowBlockPrerenderSizingInfo prerenderSizingInfo, GlobalRenderContext globalRenderContext
	) {
		Box childBox = context.childBox();
		AbsoluteSize targetSize = context.childSizeGenerator().apply(prerenderSizingInfo.preferredChildContentSize());
		LocalRenderContext childLocalRenderContext = context.localRenderContextGenerator().apply(targetSize);
		return UIPipeline.render(childBox, globalRenderContext, childLocalRenderContext);
	}

}
