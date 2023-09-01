package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;

public class FlowBlockRendererState {
	
	private final List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
	private final FlowBlockPositionTracker positionTracker = new FlowBlockPositionTracker();
	private final FlowRenderContext context;
	private final Font2D font;

	public FlowBlockRendererState(FlowRenderContext context, Font2D font) {
		this.context = context;
		this.font = font;
	}

	public FlowRenderContext flowContext() {
		return context;
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

	public FlowBlockPositionTracker positionTracker() {
		return positionTracker;
	}

	public void addChildLayoutResult(ChildLayoutResult childLayoutResult) {
		childLayoutResults.add(childLayoutResult);
	}

	public ChildLayoutResult[] childLayoutResults() {
		return childLayoutResults.toArray(new ChildLayoutResult[0]);
	}

}
