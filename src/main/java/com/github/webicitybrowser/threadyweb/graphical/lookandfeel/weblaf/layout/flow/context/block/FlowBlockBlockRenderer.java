package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.StyledUnitContext;

public final class FlowBlockBlockRenderer {

	private FlowBlockBlockRenderer() {}

	public static void renderChild(FlowBlockRendererState state, Box childBox) {
		FlowBlockRenderParameters renderParameters = FlowBlockRenderParameters.create(state, childBox);
		AbsoluteSize parentSize = renderParameters.parentSize();
		FlowBlockUnitRenderingContext context = new FlowBlockUnitRenderingContext(
			state, childBox, renderParameters,
			FlowBlockBlockRenderer::createChildLocalRenderContext,
			(childState, childSize) -> computeFallbackPreferredSize(parentSize, childSize, renderParameters.margins())
		);
		FlowBlockPrerenderSizingInfo prerenderSizingInfo = FlowBlockUnitRenderer.prerenderChild(context);
		FlowBlockChildRenderResult childRenderResult = FlowBlockUnitRenderer.generateChildUnit(context, prerenderSizingInfo);
		AbsoluteSize finalChildSize = computeFinalChildSize(renderParameters, prerenderSizingInfo, childRenderResult);
		float[] finalMargins = computeFinalMargins(state, renderParameters, finalChildSize);

		AbsolutePosition childPosition = state.positionTracker().addBox(finalChildSize, finalMargins);
		Rectangle childRect = new Rectangle(childPosition, finalChildSize);
		
		addChildToLayout(state, childBox, childRenderResult.unit(), childRect, prerenderSizingInfo.padding(), prerenderSizingInfo.borders());
	}

	private static void addChildToLayout(
		FlowBlockRendererState state, Box childBox, RenderedUnit childUnit, Rectangle childRect, float[] padding, float[] border
	) {
		
		StyledUnitContext styledUnitContext = new StyledUnitContext(childBox, childUnit, childRect.size(), padding, border);
		RenderedUnit styledUnit = state.flowContext().styledUnitGenerator().generateStyledUnit(styledUnitContext);
		state.addChildLayoutResult(new ChildLayoutResult(styledUnit, childRect));
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

	private static LocalRenderContext createChildLocalRenderContext(FlowBlockRendererState state, AbsoluteSize childSize) {
		FlowRootContextSwitch parentSwitch = state.flowContext().flowRootContextSwitch();
		AbsolutePosition predictedChildPosition = state.positionTracker().getPosition();
		AbsolutePosition offsetPredictedChildPosition = AbsolutePositionMath.sum(
			predictedChildPosition, parentSwitch.predictedPosition());
		FlowRootContextSwitch childSwitch = new FlowRootContextSwitch(
			offsetPredictedChildPosition, parentSwitch.floatContext());

		return LocalRenderContext.create(childSize, state.getFont().getMetrics(), new ContextSwitch[] {
			childSwitch
		});
	}

}
