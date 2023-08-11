package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;

public interface BoundBoxChildrenTracker {

	void addChild(BoundBox<?, ?> child);
	
	List<BoundBox<?, ?>> getChildren();
	
	List<BoundBox<?, ?>> getAdjustedBoxTree(BoundBox<?, ?> self);
	
}
