package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block.floatbox;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.FloatDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block.FlowBlockChildRenderResult;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block.FlowBlockPrerenderSizingInfo;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block.FlowBlockRendererState;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block.FlowBlockUnitRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block.FlowBlockUnitRenderingContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.FloatTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;
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
		if (floatDirection == FloatDirection.LEFT) {
			addLeftFloat(state, childUnit, blockPosition);
		} else {
			addRightFloat(state, childUnit, blockPosition);
		}
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

	private static void addLeftFloat(FlowBlockRendererState state, RenderedUnit childUnit, float blockPosition) {
		FlowRootContextSwitch flowRootContextSwitch = state.flowContext().flowRootContextSwitch();
		FloatTracker floatTracker = flowRootContextSwitch.floatContext().getFloatTracker();
		AbsolutePosition offsetPosition = flowRootContextSwitch.predictedPosition();
		float offsetBlockPosition = blockPosition + offsetPosition.y();
		float posXLeft = floatTracker.getLeftInlineOffset(offsetBlockPosition);
		// TODO: Handle overflow and left/right collisions
		AbsolutePosition floatPosition = new AbsolutePosition(posXLeft, blockPosition);
		AbsolutePosition offsetFloatPosition = new AbsolutePosition(posXLeft, offsetBlockPosition);
		AbsoluteSize floatSize = childUnit.fitSize();
		floatTracker.addLeftFloat(new Rectangle(offsetFloatPosition, floatSize));
		state.addChildLayoutResult(new ChildLayoutResult(childUnit, new Rectangle(floatPosition, floatSize)));
	}

	private static void addRightFloat(FlowBlockRendererState state, RenderedUnit childUnit, float blockPosition) {
		FlowRootContextSwitch flowRootContextSwitch = state.flowContext().flowRootContextSwitch();
		FloatTracker floatTracker = flowRootContextSwitch.floatContext().getFloatTracker();
		AbsoluteSize parentSize = state.getLocalRenderContext().getPreferredSize();
		float posXRight =
			parentSize.width() -
			floatTracker.getRightInlineOffset(blockPosition, parentSize.width()) -
			childUnit.fitSize().width();
		AbsolutePosition floatPosition = new AbsolutePosition(posXRight, blockPosition);
		AbsolutePosition offsetPosition = flowRootContextSwitch.predictedPosition();
		AbsolutePosition offsetFloatPosition = new AbsolutePosition(posXRight, blockPosition + offsetPosition.y());
		AbsoluteSize floatSize = childUnit.fitSize();
		floatTracker.addRightFloat(new Rectangle(offsetFloatPosition, floatSize));
		state.addChildLayoutResult(new ChildLayoutResult(childUnit, new Rectangle(floatPosition, floatSize)));
	}

	private static AbsoluteSize computeFloatBoxPreferredSize(FlowBlockRendererState state, AbsoluteSize enforcedSize) {
		return enforcedSize;
	}

	private static LocalRenderContext createLocalRenderContext(FlowBlockRendererState state, AbsoluteSize preferredSize) {
		// We do not pass the flow root context switch, as the float establishes its own root context
		// TODO: Make sure we are passing the correct font metrics
		return LocalRenderContext.create(preferredSize, state.getLocalRenderContext().getParentFontMetrics(), new ContextSwitch[0]);
	}

}
