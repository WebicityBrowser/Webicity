package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;

public interface ChildrenBox extends Box {

	BoundBoxChildrenTracker getChildrenTracker();
	
	default List<BoundBox<?, ?>> getAdjustedBoxTree(BoundBox<?, ?> self) {
		return getChildrenTracker().getAdjustedBoxTree(self);
	};
	
}
