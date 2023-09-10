package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils.LayoutSizingContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.StyledUnitContext;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowBlockBlockRenderer {

	private FlowBlockBlockRenderer() {}

	public static void renderChild(FlowBlockRendererState state, Box childBox) {
		FlowBlockRenderParameters renderParameters = FlowBlockRenderParameters.create(state, childBox);
		FlowBlockPrerenderSizingInfo prerenderSizingInfo = prerenderChild(state, childBox, renderParameters);
		FlowBlockChildRenderResult childRenderResult = generateChildUnit(state, childBox, prerenderSizingInfo);
		AbsoluteSize finalChildSize = computeFinalChildSize(renderParameters, prerenderSizingInfo, childRenderResult);
		float[] finalMargins = computeFinalMargins(state, renderParameters, finalChildSize);

		AbsolutePosition childPosition = state.positionTracker().addBox(finalChildSize, finalMargins);
		Rectangle childRect = new Rectangle(childPosition, finalChildSize);
		
		addChildToLayout(state, childBox, childRenderResult.unit(), childRect, prerenderSizingInfo.totalPadding());
	}

	private static void addChildToLayout(
		FlowBlockRendererState state, Box childBox, RenderedUnit childUnit, Rectangle childRect, float[] paddings
	) {
		
		StyledUnitContext styledUnitContext = new StyledUnitContext(childBox, childUnit, childRect.size(), paddings);
		RenderedUnit styledUnit = state.flowContext().styledUnitGenerator().generateStyledUnit(styledUnitContext);
		state.addChildLayoutResult(new ChildLayoutResult(styledUnit, childRect));
	}

	private static FlowBlockPrerenderSizingInfo prerenderChild(FlowBlockRendererState state, Box childBox, FlowBlockRenderParameters renderParameters) {
		AbsoluteSize parentSize = state.getLocalRenderContext().getPreferredSize();
		LayoutSizingContext layoutSizingContext = createLayoutSizingContext(state, childBox, renderParameters);
		AbsoluteSize enforcedSize = computePreferredSize(childBox, layoutSizingContext);
		AbsoluteSize precomputedSize = computeFallbackPreferredSize(parentSize, enforcedSize, renderParameters.margins());

		AbsoluteSize enforcedChildSize = LayoutSizeUtils.subtractPadding(enforcedSize, renderParameters.totalPadding());
		AbsoluteSize precomputedChildSize = LayoutSizeUtils.subtractPadding(precomputedSize, renderParameters.totalPadding());

		return new FlowBlockPrerenderSizingInfo(enforcedChildSize, precomputedChildSize, layoutSizingContext);
	}

	private static FlowBlockChildRenderResult generateChildUnit(FlowBlockRendererState state, Box childBox, FlowBlockPrerenderSizingInfo prerenderSizingInfo) {
		AbsoluteSize precomputedSize = prerenderSizingInfo.precomputedChildSize();
		RenderedUnit childUnit = renderChildUnit(state, childBox, precomputedSize);
		AbsoluteSize adjustedSize = FlowSizeUtils.enforcePreferredSize(childUnit.fitSize(), prerenderSizingInfo.enforcedChildSize());
		precomputedSize = FlowBlockSizeCalculations.clipContentSize(childBox.styleDirectives(), adjustedSize, prerenderSizingInfo);
		RenderedUnit unit = renderChildUnit(state, childBox, precomputedSize);
		adjustedSize = FlowSizeUtils.enforcePreferredSize(unit.fitSize(), precomputedSize);

		return new FlowBlockChildRenderResult(unit, adjustedSize);
	}

	private static AbsoluteSize computeFinalChildSize(
		FlowBlockRenderParameters renderParameters, FlowBlockPrerenderSizingInfo prerenderSizingInfo, FlowBlockChildRenderResult childResult
	) {
		AbsoluteSize adjustedSize = LayoutSizeUtils.addPadding(childResult.adjustedSize(), prerenderSizingInfo.totalPadding());
		return stretchToParentSize(
			adjustedSize, renderParameters.parentSize(),
			prerenderSizingInfo.enforcedChildSize(), renderParameters.margins());
	}

	private static float[] computeFinalMargins(FlowBlockRendererState state, FlowBlockRenderParameters renderParameters, AbsoluteSize adjustedSize) {
		float[] adjustedMargins = FlowBlockMarginCalculations.adjustMargins(state, renderParameters.margins(), adjustedSize);
		return FlowBlockMarginCalculations.collapseOverflowMargins(renderParameters.parentSize(), adjustedSize, adjustedMargins);
	}

	private static LayoutSizingContext createLayoutSizingContext(FlowBlockRendererState state, Box childBox, FlowBlockRenderParameters renderParameters) {
		FontMetrics fontMetrics = state.getFont().getMetrics();
		Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator =
			isHorizontal -> FlowUtils.createSizeCalculationContext(state.flowContext(), fontMetrics, isHorizontal);

		return LayoutSizeUtils.createLayoutSizingContext(
			childBox.styleDirectives(), sizeCalculationContextGenerator, renderParameters.totalPadding()
		);
	}

	private static AbsoluteSize computePreferredSize(Box childBox, LayoutSizingContext layoutSizingContext) {
		return LayoutSizeUtils.computePreferredSize(childBox.styleDirectives(), layoutSizingContext);
	}

	private static RenderedUnit renderChildUnit(FlowBlockRendererState state, Box childBox, AbsoluteSize contentSize) {
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		LocalRenderContext childLocalRenderContext = createChildLocalRenderContext(state, contentSize);
		return UIPipeline.render(childBox, globalRenderContext, childLocalRenderContext);
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
		if (margins[0] == RelativeDimension.UNBOUNDED || margins[1] == RelativeDimension.UNBOUNDED) {
			return adjustedSize;
		}
		float marginOffset = Math.max(0, margins[0]) + Math.max(0, margins[1]);
		float stretchedPreferredWidth = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			adjustedSize.width() :
			Math.max(parentSize.width() - marginOffset, adjustedSize.width());
		float stretchedPreferredHeight = adjustedSize.height();

		return new AbsoluteSize(stretchedPreferredWidth, stretchedPreferredHeight);
	}

	private static LocalRenderContext createChildLocalRenderContext(FlowBlockRendererState state, AbsoluteSize preferredSize) {
		return LocalRenderContext.create(preferredSize, state.getFont().getMetrics(), new ContextSwitch[0]);
	}

}
