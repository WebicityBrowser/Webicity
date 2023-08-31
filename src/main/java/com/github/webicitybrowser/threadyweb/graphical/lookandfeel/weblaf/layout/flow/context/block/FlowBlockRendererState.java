package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.CursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineCursorTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;

public class FlowBlockRendererState {
	
	private final List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
	private final CursorTracker cursorTracker;
	private final FlowRenderContext context;
	private final Font2D font;

	public FlowBlockRendererState(LineDimensionConverter lineDimensionConverter, FlowRenderContext context, Font2D font) {
		this.cursorTracker = new LineCursorTracker(lineDimensionConverter);
		this.context = context;
		this.font = font;
	}

	public GlobalRenderContext getGlobalRenderContext() {
		return context.globalRenderContext();
	}

	public LocalRenderContext getLocalRenderContext() {
		return context.localRenderContext();
	}

	public Font2D getFont() {
		return font;
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
