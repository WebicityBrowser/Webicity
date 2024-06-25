package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRenderContext;

public class FlowBlockRendererState {
	
	private final List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
	private final FlowBlockPositionTracker positionTracker = new FlowBlockPositionTracker();
	private final FlowRenderContext context;

	public FlowBlockRendererState(FlowRenderContext context) {
		this.context = context;
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
