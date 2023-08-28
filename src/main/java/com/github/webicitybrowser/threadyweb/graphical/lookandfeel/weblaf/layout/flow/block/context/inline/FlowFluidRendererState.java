package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.FlowBlockRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.LineMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidation;

public class FlowFluidRendererState {

	private final LineDimensionConverter dimensionConverter;
	private final FlowBlockRenderContext context;

	private final List<LineBox> lines = new ArrayList<>();
	private final TextConsolidation textConsolidation = TextConsolidation.create();

	private LineBox currentLine;

	public FlowFluidRendererState(LineDimensionConverter dimensionConverter, FlowBlockRenderContext context) {
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

	public GlobalRenderContext getGlobalRenderContext() {
		return context.globalRenderContext();
	}

	public LocalRenderContext getLocalRenderContext() {
		return context.localRenderContext();
	}

	public TextConsolidation getTextConsolidation() {
		return textConsolidation;
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
