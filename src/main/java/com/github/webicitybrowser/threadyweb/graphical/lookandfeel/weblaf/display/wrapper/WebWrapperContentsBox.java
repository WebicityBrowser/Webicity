package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public record WebWrapperContentsBox(Component owningComponent, DirectivePool styleDirectives, List<BoundBox<?, ?>> childBoxes) implements WebWrapperBox {

	@Override
	public List<BoundBox<?, ?>> getAdjustedBoxTree(BoundBox<?, ?> self) {
		return childBoxes;
	};
	
}