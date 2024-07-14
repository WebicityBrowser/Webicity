package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.floatbox;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.FloatDirective;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockChildRenderResult;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockPrerenderSizingInfo;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockUnitRenderer;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockUnitRenderingContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatTracker;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutMarginCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitContext;
import com.github.webicitybrowser.threadyweb.graphical.value.FloatDirection;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowBlockFloatRenderer {

	private FlowBlockFloatRenderer() {}

	public static boolean isFloatBox(Box childBox) {
		return childBox
			.componentUI()
			.getStyleReference(FloatDirective.class).get()
			.getFloatDirection() != FloatDirection.NONE;
	}

	public static void addFloatBoxToLine(
		FlowBlockRenderContext state, RenderedUnit childUnit, float blockPosition
	) {
		FloatDirection floatDirection = childUnit
			.componentUI()
			.getStyleReference(FloatDirective.class).get()
			.getFloatDirection();
		addFloat(state, childUnit, blockPosition, floatDirection);
	}

	public static RenderedUnit renderFloatBoxUnit(FlowBlockRenderContext state, Box childBox) {
		BoxOffsetDimensions renderParameters = BoxOffsetDimensions.create(state.flowContext().layoutRenderContext(), childBox.componentUI());
		FlowBlockUnitRenderingContext context = new FlowBlockUnitRenderingContext(
			state, childBox, renderParameters,
			FlowBlockFloatRenderer::createLocalRenderContext,
			FlowBlockFloatRenderer::computeFloatBoxPreferredSize
		);

		FlowBlockPrerenderSizingInfo prerenderSizingInfo = FlowBlockUnitRenderer.prerenderChild(context);
		FlowBlockChildRenderResult childRenderResult = FlowBlockUnitRenderer.generateChildUnit(context, prerenderSizingInfo);
		AbsoluteSize styledUnitSize = LayoutSizeUtils.addPadding(childRenderResult.adjustedSize(), renderParameters.totalPadding());

		StyledUnitContext styledUnitContext = new StyledUnitContext(
			childRenderResult.unit(), styledUnitSize,
			prerenderSizingInfo.sizingContext().boxOffsetDimensions()
		);
		RenderedUnit styledUnit = state.flowConfig().styledUnitGenerator().generateStyledUnit(styledUnitContext);
		
		// TODO: Clamp the float size

		return styledUnit;
	}

	private static void addFloat(FlowBlockRenderContext state, RenderedUnit childUnit, float blockPosition, FloatDirection floatDirection) {
		FlowRootContextSwitch flowRootContextSwitch = state.flowContext().flowRootContextSwitch();
		FloatTracker floatTracker = flowRootContextSwitch.floatContext().getFloatTracker();
		AbsoluteSize parentSize = state.getLocalRenderContext().preferredSize();
		
		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(
			state.getGlobalRenderContext(), state.getLocalRenderContext(), childUnit.componentUI());
		float[] margins = LayoutMarginCalculations.computeMargins(sizeCalculationContext, childUnit.styleDirectives());
		margins = LayoutMarginCalculations.zeroAutoMargins(margins);

		AbsoluteSize floatInnerSize = childUnit.fitSize();
		AbsoluteSize floatMarginSize = LayoutSizeUtils.addPadding(floatInnerSize, margins);

		float offsetBlockPosition = floatTracker.getFitBlockPosition(blockPosition, parentSize.width(), floatInnerSize);
		float posInline = floatDirection == FloatDirection.LEFT ?
			floatTracker.getLeftInlineOffset(offsetBlockPosition) :
			parentSize.width() - floatTracker.getRightInlineOffset(offsetBlockPosition, parentSize.width()) - childUnit.fitSize().width();

		AbsolutePosition floatMarginPosition = new AbsolutePosition(posInline, offsetBlockPosition);
		AbsolutePosition floatPosition = floatDirection == FloatDirection.LEFT ?
			new AbsolutePosition(posInline + margins[0], offsetBlockPosition + margins[2]) :
			new AbsolutePosition(posInline - margins[1], offsetBlockPosition + margins[2]);

		if (floatDirection == FloatDirection.LEFT) {
			floatTracker.addLeftFloat(new Rectangle(floatMarginPosition, floatMarginSize));
		} else {
			floatTracker.addRightFloat(new Rectangle(floatMarginPosition, floatMarginSize));
		}

		Rectangle boundsBox = new Rectangle(floatPosition, floatInnerSize);
		state.addChildLayoutResult(new ChildLayoutResult(childUnit, boundsBox));

		// TODO: Handle overflow and left/right collisions
		// TODO: Simplify this method
	}

	private static AbsoluteSize computeFloatBoxPreferredSize(FlowBlockRenderContext state, AbsoluteSize enforcedSize) {
		return enforcedSize;
	}

	private static LocalRenderContext createLocalRenderContext(FlowBlockRenderContext state, AbsoluteSize preferredSize) {
		// We do not pass the flow root context switch, as the float establishes its own root context
		return LocalRenderContext.create(preferredSize, new ContextSwitch[0]);
	}

}
