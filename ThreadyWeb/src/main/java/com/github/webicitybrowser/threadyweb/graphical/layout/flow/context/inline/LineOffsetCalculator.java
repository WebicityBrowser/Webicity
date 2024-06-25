package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.TextAlignDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.TextAlignDirective.TextAlign;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension.LineDirection;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatTracker;

public final class LineOffsetCalculator {
	
	private LineOffsetCalculator() {}

	public static LineDimension offsetLinePosition(LineDimension linePosition, LineBox line, FlowRootContextSwitch flowRootContextSwitch) {
		LineDimension actualLinePosition = linePosition;
		// TODO: Handle non-LTR line direction
		if (flowRootContextSwitch != null && line.getLineDirection() == LineDirection.LTR) {
			actualLinePosition = offsetLTRLinePosition(linePosition, line, flowRootContextSwitch);
		}
		
		actualLinePosition = offsetLinePositionByAlignment(actualLinePosition, line);

		return actualLinePosition;
	}

	private static LineDimension offsetLTRLinePosition(LineDimension linePosition, LineBox line, FlowRootContextSwitch flowRootContextSwitch) {
		float offsetY = flowRootContextSwitch.predictedPosition().y() + linePosition.depth();
		FloatTracker floatTracker = flowRootContextSwitch.floatContext().getFloatTracker();
		float lineXOffset = floatTracker.getLeftInlineOffset(offsetY);
		return new LineDimension(linePosition.run() + lineXOffset, linePosition.depth(), line.getLineDirection());
	}

	private static LineDimension offsetLinePositionByAlignment(LineDimension linePosition, LineBox line) {
		TextAlign textAlign = getLineTextAlign(line);
		AbsoluteSize lineSize = line.getSize();
			float lineRun = LineDimensionConverter.convertToLineDimension(lineSize, line.getLineDirection()).run();
			float maxLineRun = line.getMaxLineSize().run();
		if (lineRun >= maxLineRun) return linePosition;
		if (textAlign == TextAlign.START || textAlign == TextAlign.LEFT) {
			return linePosition;
		} else if (textAlign == TextAlign.END || textAlign == TextAlign.RIGHT) {
			return new LineDimension(linePosition.run() + maxLineRun - lineRun, linePosition.depth(), line.getLineDirection());
		} else if (textAlign == TextAlign.CENTER) {
			return new LineDimension(linePosition.run() + maxLineRun / 2 - lineRun / 2, linePosition.depth(), line.getLineDirection());
		} else {
			new IllegalStateException("Unsupported text align: " + textAlign).printStackTrace();
			//throw new IllegalStateException("Unsupported text align: " + textAlign);
			return linePosition;
		}
	}

	private static TextAlign getLineTextAlign(LineBox lineBox) {
		return lineBox
			.getLineStyles()
			.inheritDirectiveOrEmpty(TextAlignDirective.class)
			.map(TextAlignDirective::getTextAlign)
			.orElse(TextAlign.START);
	}

}
