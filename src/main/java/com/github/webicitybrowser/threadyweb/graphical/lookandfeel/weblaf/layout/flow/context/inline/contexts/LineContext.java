package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.contexts;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.LineBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.marker.LineMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;

public class LineContext {

	private final LineDimensionConverter dimensionConverter;
	private final FlowRenderContext context;
	private final List<LineBox> lines = new ArrayList<>();

	private LineBox currentLine;

	public LineContext(LineDimensionConverter dimensionConverter, FlowRenderContext context) {
		this.dimensionConverter = dimensionConverter;
		this.context = context;
		
		startNewLine();
	}
	
	public LineBox currentLine() {
		return this.currentLine;
	}

	public LineBox startNewLine() {
		// The code has the same effect as without the if statement,
		// but the if statement skips some unnecessary work.
		if (currentLine != null && currentLine.isEmpty()) {
			return currentLine;
		}

		LineBox newLine = new LineBox(dimensionConverter, context.innerUnitGenerator());
		copyUnresolvedMarkers(newLine);
		this.currentLine = newLine;
		lines.add(currentLine);
		return currentLine;
	}

	public List<LineBox> lines() {
		return this.lines;
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
