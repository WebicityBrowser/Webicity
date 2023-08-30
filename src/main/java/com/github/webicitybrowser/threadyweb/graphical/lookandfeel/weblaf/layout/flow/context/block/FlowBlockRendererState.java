package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.CursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineCursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;

public class FlowBlockRendererState {
	
	private final List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
	private final CursorTracker cursorTracker;
	private final FlowRenderContext context;

	public FlowBlockRendererState(LineDimensionConverter lineDimensionConverter, FlowRenderContext context) {
		this.cursorTracker = new LineCursorTracker(lineDimensionConverter);
		this.context = context;
	}

	public GlobalRenderContext getGlobalRenderContext() {
		return context.globalRenderContext();
	}

	public LocalRenderContext getLocalRenderContext() {
		return context.localRenderContext();
	}

	public CursorTracker cursorTracker() {
		return cursorTracker;
	}

	public void addChildLayoutResult(ChildLayoutResult childLayoutResult) {
		childLayoutResults.add(childLayoutResult);
	}

	public ChildLayoutResult[] childLayoutResults() {
		return childLayoutResults.toArray(new ChildLayoutResult[0]);
	}

}
