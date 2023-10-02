package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline;

import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimension.LineDirection;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.FloatTracker;

public final class LineOffsetCalculator {
	
	private LineOffsetCalculator() {}

	public static LineDimension offsetLinePosition(LineDimension linePosition, LineBox line, FlowRootContextSwitch flowRootContextSwitch) {
		LineDimension actualLinePosition = linePosition;
		// TODO: Handle non-LTR line direction
		if (flowRootContextSwitch != null && line.getLineDirection() == LineDirection.LTR) {
			return offsetLTRLinePosition(linePosition, line, flowRootContextSwitch);
		}

		return actualLinePosition;
	}

	private static LineDimension offsetLTRLinePosition(LineDimension linePosition, LineBox line, FlowRootContextSwitch flowRootContextSwitch) {
		float offsetY = flowRootContextSwitch.predictedPosition().y() + linePosition.depth();
		FloatTracker floatTracker = flowRootContextSwitch.floatContext().getFloatTracker();
		float lineXOffset = floatTracker.getLeftInlineOffset(offsetY);
		return new LineDimension(linePosition.run() + lineXOffset, linePosition.depth(), line.getLineDirection());
	}

}
