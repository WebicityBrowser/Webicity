package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.contexts.LineContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatTracker;

public final class FlowInlineRendererUtil {
	
	private FlowInlineRendererUtil() {}

	public static void startNewLineIfNotFits(FlowInlineRendererState state, AbsoluteSize preferredSize) {
		LineContext lineContext = state.lineContext();
		LineBox currentLine = lineContext.currentLine();
		if (!currentLine.canFit(preferredSize)) {
			startNewLine(state);
		}
	}

	public static void startNewLine(FlowInlineRendererState state) {
		LineContext lineContext = state.lineContext();
		lineContext.startNewLine(position -> calculateMaxLineSize(state, position));
	}

	private static LineDimension calculateMaxLineSize(FlowInlineRendererState state, AbsolutePosition position) {
		return new LineDimension(
			calculateRemainingLineWidth(state, position),
			RelativeDimension.UNBOUNDED,
			state.lineContext().lineDirection());
	}

	private static float calculateRemainingLineWidth(FlowInlineRendererState state, AbsolutePosition currentPositionOffset) {
		// TODO: Non-LTR line direction
		FlowRootContextSwitch contextSwitch = state.flowContext().flowRootContextSwitch();
		float parentWidth = state.getLocalRenderContext().preferredSize().width();
		if (parentWidth == RelativeDimension.UNBOUNDED) {
			return parentWidth;
		}

		float availableWidth = parentWidth;
		if (availableWidth == RelativeDimension.UNBOUNDED) {
			return availableWidth;
		}

		if (contextSwitch != null) {
			FloatTracker floatTracker = contextSwitch.floatContext().getFloatTracker();
			AbsolutePosition currentPosition = AbsoluteDimensionsMath.sum(contextSwitch.predictedPosition(), currentPositionOffset, AbsolutePosition::new);
			availableWidth -= floatTracker.getLeftInlineOffset(currentPosition.y());
			availableWidth -= floatTracker.getRightInlineOffset(currentPosition.y(), parentWidth);
		}
		return Math.max(availableWidth, 0);
	}

}
