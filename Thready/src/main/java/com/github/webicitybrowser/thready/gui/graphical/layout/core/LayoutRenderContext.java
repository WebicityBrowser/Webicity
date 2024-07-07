package com.github.webicitybrowser.thready.gui.graphical.layout.core;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;

public record LayoutRenderContext(
	GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, TreeTracker treeTracker
) {
	
	public DirectivePool layoutDirectives() {
		return treeTracker.parentBox().styleDirectives();
	}

}
