package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.LineMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;

public class FlowFluidRendererState {

	private final LineDimensionConverter dimensionConverter;
	private final GlobalRenderContext globalRenderContext;
	private final LocalRenderContext localRenderContext;
	private final UIDisplay<?, ?, InnerDisplayUnit> innerDisplay;
	private final List<LineBox> lines = new ArrayList<>();

	private LineBox currentLine;

	public FlowFluidRendererState(
		LineDimensionConverter dimensionConverter, GlobalRenderContext globalRenderContext,
		LocalRenderContext localRenderContext, UIDisplay<?, ?, InnerDisplayUnit> innerDisplay
	) {
		this.dimensionConverter = dimensionConverter;
		this.globalRenderContext = globalRenderContext;
		this.localRenderContext = localRenderContext;
		this.innerDisplay = innerDisplay;
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

		LineBox newLine = new LineBox(dimensionConverter, innerDisplay);
		copyUnresolvedMarkers(newLine);
		this.currentLine = newLine;
		lines.add(currentLine);
		return currentLine;
	}

	public List<LineBox> lines() {
		return this.lines;
	}

	public GlobalRenderContext getGlobalRenderContext() {
		return globalRenderContext;
	}

	public LocalRenderContext getLocalRenderContext() {
		return localRenderContext;
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
