package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.PrerenderMessage;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface Box {
	
	Component owningComponent();

	DirectivePool styleDirectives();

	default void message(PrerenderMessage message) {};
	
	default boolean isFluid() {
		return false;
	};

	default List<BoundBox<?, ?>> getAdjustedBoxTree(BoundBox<?, ?> self) {
		return List.of(self);
	};
	
}
