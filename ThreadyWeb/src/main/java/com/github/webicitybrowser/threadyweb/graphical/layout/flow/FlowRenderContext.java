package com.github.webicitybrowser.threadyweb.graphical.layout.flow;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;

public record FlowRenderContext(
	LayoutRenderContext layoutRenderContext,
	FlowRootContextSwitch flowRootContextSwitch
) {

	public GlobalRenderContext globalRenderContext() {
		return layoutRenderContext.globalRenderContext();
	}

	public LocalRenderContext localRenderContext() {
		return layoutRenderContext.localRenderContext();
	}
	
}
