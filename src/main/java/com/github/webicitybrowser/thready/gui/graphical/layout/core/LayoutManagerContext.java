package com.github.webicitybrowser.thready.gui.graphical.layout.core;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;

public record LayoutManagerContext(
	Box parentBox, List<Box> children, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
) {
	
	public DirectivePool layoutDirectives() {
		return parentBox.styleDirectives();
	}

}
