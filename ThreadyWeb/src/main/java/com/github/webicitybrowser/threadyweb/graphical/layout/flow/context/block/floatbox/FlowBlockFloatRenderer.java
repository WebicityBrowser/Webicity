package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.floatbox;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.FloatDirective;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockChildRenderResult;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockMarginCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockPrerenderSizingInfo;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockRendererState;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockUnitRenderer;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockUnitRenderingContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatTracker;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitContext;
import com.github.webicitybrowser.threadyweb.graphical.value.FloatDirection;

public final class FlowBlockFloatRenderer {

	private FlowBlockFloatRenderer() {}

	public static boolean isFloatBox(Box childBox) {
		return childBox
			.styleDirectives()
			.getDirectiveOrEmpty(FloatDirective.class)
			.map(floatDirective -> floatDirective.getFloatDirection() != FloatDirection.NONE)
			.orElse(false);
	}

	public static void addFloatBoxToLine(
		FlowBlockRendererState state, RenderedUnit childUnit, DirectivePool styleDirectives, float blockPosition
	) {
		FloatDirection floatDirection = styleDirectives
			.getDirectiveOrEmpty(FloatDirective.class)
			.map(FloatDirective::getFloatDirection)
			.orElseThrow(() -> new IllegalStateException("Float box has no float directive"));
		addFloat(state, childUnit, blockPosition, floatDirection);
	}

	public static RenderedUnit renderFloatBoxUnit(FlowBlockRendererState state, Box childBox) {
		BoxOffsetDimensions renderParameters = BoxOffsetDimensions.create(state, childBox);
		FlowBlockUnitRenderingContext context = new FlowBlockUnitRenderingContext(
			state, childBox, renderParameters,
			FlowBlockFloatRenderer::createLocalRenderContext,
			FlowBlockFloatRenderer::computeFloatBoxPreferredSize
		);

		FlowBlockPrerenderSizingInfo prerenderSizingInfo = FlowBlockUnitRenderer.prerenderChild(context);
		FlowBlockChildRenderResult childRenderResult = FlowBlockUnitRenderer.generateChildUnit(context, prerenderSizingInfo);
		AbsoluteSize styledUnitSize = LayoutSizeUtils.addPadding(childRenderResult.adjustedSize(), renderParameters.totalPadding());

		StyledUnitContext styledUnitContext = new StyledUnitContext(
			childBox, childRenderResult.unit(), styledUnitSize,
			prerenderSizingInfo.sizingContext().boxOffsetDimensions()
		);
		RenderedUnit styledUnit = state.flowContext().styledUnitGenerator().generateStyledUnit(styledUnitContext);
		
		// TODO: Clamp the float size

		return styledUnit;
	}

	private static void addFloat(FlowBlockRendererState state, RenderedUnit childUnit, float blockPosition, FloatDirection floatDirection) {
		FlowRootContextSwitch flowRootContextSwitch = state.flowContext().flowRootContextSwitch();
		FloatTracker floatTracker = flowRootContextSwitch.floatContext().getFloatTracker();
		AbsolutePosition trackerPositionOffset = flowRootContextSwitch.predictedPosition();
		AbsoluteSize parentSize = state.getLocalRenderContext().preferredSize();
		
		float[] margins = FlowBlockMarginCalculations.computeMargins(state, childUnit.styleDirectives());
		for (int i = 0; i < 4; i++) {
			margins[i] = margins[i] == RelativeDimension.UNBOUNDED ? 0 : margins[i];
		}

		AbsoluteSize floatInnerSize = childUnit.fitSize();
		AbsoluteSize floatMarginSize = LayoutSizeUtils.addPadding(floatInnerSize, margins);

		float offsetBlockPosition = blockPosition + trackerPositionOffset.y();
		float adjsutedOffsetBlockPosition = floatTracker.getFitBlockPosition(offsetBlockPosition, parentSize.width(), floatInnerSize);
		float adjustedBlockPosition = adjsutedOffsetBlockPosition - trackerPositionOffset.y();
		float posInline = floatDirection == FloatDirection.LEFT ?
			floatTracker.getLeftInlineOffset(adjustedBlockPosition + trackerPositionOffset.y()) :
			parentSize.width() - floatTracker.getRightInlineOffset(adjustedBlockPosition, parentSize.width()) - childUnit.fitSize().width();

		AbsolutePosition floatMarginPosition = new AbsolutePosition(posInline, adjsutedOffsetBlockPosition);
		AbsolutePosition floatPosition = floatDirection == FloatDirection.LEFT ?
			new AbsolutePosition(posInline + margins[0], adjustedBlockPosition + margins[2]) :
			new AbsolutePosition(posInline - margins[1], adjustedBlockPosition + margins[2]);

		if (floatDirection == FloatDirection.LEFT) {
			floatTracker.addLeftFloat(new Rectangle(floatMarginPosition, floatMarginSize));
		} else {
			floatTracker.addRightFloat(new Rectangle(floatMarginPosition, floatMarginSize));
		}
		state.addChildLayoutResult(new ChildLayoutResult(childUnit, new Rectangle(floatPosition, floatInnerSize)));

		// TODO: Handle overflow and left/right collisions
		// TODO: Simplify this method
	}

	private static AbsoluteSize computeFloatBoxPreferredSize(FlowBlockRendererState state, AbsoluteSize enforcedSize) {
		return enforcedSize;
	}

	private static LocalRenderContext createLocalRenderContext(FlowBlockRendererState state, AbsoluteSize preferredSize) {
		// We do not pass the flow root context switch, as the float establishes its own root context
		return LocalRenderContext.create(preferredSize, new ContextSwitch[0]);
	}

}
