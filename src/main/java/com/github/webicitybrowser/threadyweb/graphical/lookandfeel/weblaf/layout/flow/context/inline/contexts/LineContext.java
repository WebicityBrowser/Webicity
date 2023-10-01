package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.contexts;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.LineBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.marker.LineMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimension.LineDirection;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;

public class LineContext {

	private final LineDirection lineDirection;
	private final FlowRenderContext context;
	private final List<LineBox> lines = new ArrayList<>();

	private LineBox currentLine;

	public LineContext(LineDirection lineDirection, FlowRenderContext context) {
		this.lineDirection = lineDirection;
		this.context = context;
	}
	
	public LineBox currentLine() {
		return this.currentLine;
	}

	public LineBox startNewLine(Function<AbsolutePosition, LineDimension> maxLineSizeGenerator) {
		// The code has the same effect as without the if statement,
		// but the if statement skips some unnecessary work.
		if (currentLine != null && currentLine.isEmpty()) {
			return currentLine;
		}

		AbsolutePosition nextLinePosition = determineNextLinePosition();
		LineDimension maxLineSize = maxLineSizeGenerator.apply(nextLinePosition);

		LineBox newLine = new LineBox(maxLineSize, context.innerUnitGenerator());
		newLine.setEstimatedPosition(nextLinePosition);
		copyUnresolvedMarkers(newLine);
		this.currentLine = newLine;
		lines.add(currentLine);
		return currentLine;
	}

	public List<LineBox> lines() {
		return this.lines;
	}

	public LineDirection lineDirection() {
		return this.lineDirection;
	}

	private AbsolutePosition determineNextLinePosition() {
		if (currentLine == null) {
			return new AbsolutePosition(0, 0);
		}

		LineDimension prevLinePosition = LineDimensionConverter.convertToLineDimension(currentLine.getEstimatedPosition(), lineDirection);
		float prevLineBlockSize = currentLine.getEstimatedBlockSize();
		LineDimension nextLinePosition = new LineDimension(0, prevLinePosition.depth() + prevLineBlockSize, lineDirection);

		return LineDimensionConverter.convertToAbsolutePosition(
			nextLinePosition, new AbsoluteSize(0, 0), new AbsoluteSize(0, 0));
	}

	private void copyUnresolvedMarkers(LineBox newLine) {
		if (currentLine == null) return;
		
		for (LineMarker lineMarker: currentLine.getActiveMarkers()) {
			if (lineMarker instanceof UnitEnterMarker unitEnterMarker) {
				newLine.addMarker(unitEnterMarker.split());
			}
		}
	}

}
