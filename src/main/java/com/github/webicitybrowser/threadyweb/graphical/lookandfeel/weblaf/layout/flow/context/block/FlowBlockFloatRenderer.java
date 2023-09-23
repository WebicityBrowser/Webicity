package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.FloatDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.FloatTracker;
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

	public static void addFloatBoxToLine(FlowBlockRendererState state, Box childBox, float blockPosition) {
		RenderedUnit childUnit = renderFloatBoxUnit(state, childBox);

		FloatDirection floatDirection = childBox
			.styleDirectives()
			.getDirectiveOrEmpty(FloatDirective.class)
			.map(FloatDirective::getFloatDirection)
			.orElseThrow(() -> new IllegalStateException("Float box has no float directive"));
		if (floatDirection == FloatDirection.LEFT) {
			addLeftFloat(state, childUnit, blockPosition);
		} else {
			addRightFloat(state, childUnit, blockPosition);
		}
	}

	private static void addLeftFloat(FlowBlockRendererState state, RenderedUnit childUnit, float blockPosition) {
		FlowRootContextSwitch flowRootContextSwitch = state.flowContext().flowRootContextSwitch();
		FloatTracker floatTracker = flowRootContextSwitch.floatContext().getFloatTracker();
		float posXLeft = floatTracker.getLeftInlineOffset(blockPosition);
		// TODO: Handle overflow and left/right collisions
		AbsolutePosition floatPosition = new AbsolutePosition(posXLeft, blockPosition);
		AbsolutePosition offsetPosition = flowRootContextSwitch.predictedPosition();
		AbsolutePosition offsetFloatPosition = new AbsolutePosition(posXLeft, blockPosition + offsetPosition.y());
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

	private static RenderedUnit renderFloatBoxUnit(FlowBlockRendererState state, Box childBox) {
		LocalRenderContext localRenderContext = state.getLocalRenderContext();
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		AbsoluteSize preferredSize = computeFloatBoxPreferredSize(state, childBox);
		LocalRenderContext childLocalRenderContext = LocalRenderContext.create(
			preferredSize, localRenderContext.getParentFontMetrics(),
			new ContextSwitch[0]); // We do not pass the flow root context switch, as the float establishes its own root context
		RenderedUnit childUnit = UIPipeline.render(childBox, globalRenderContext, childLocalRenderContext);

		AbsoluteSize childSize = childUnit.fitSize();
		float parentWidth = localRenderContext.getPreferredSize().width();
		if (
			childSize.width() > parentWidth &&
			parentWidth != RelativeDimension.UNBOUNDED &&
			preferredSize.width() == RelativeDimension.UNBOUNDED
		) {
			childSize = new AbsoluteSize(parentWidth, preferredSize.height());
		}

		return childUnit;
	}

	private static AbsoluteSize computeFloatBoxPreferredSize(FlowBlockRendererState state, Box childBox) {
		return new AbsoluteSize(RelativeDimension.UNBOUNDED, RelativeDimension.UNBOUNDED);
	}

}
