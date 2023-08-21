package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public record WebWrapperContentsBox(
	UIDisplay<?, ?, ?> display, Component owningComponent, DirectivePool styleDirectives, List<Box> childBoxes
) implements WebWrapperBox {

	@Override
	public List<Box> getAdjustedBoxTree() {
		return childBoxes;
	};
	
}