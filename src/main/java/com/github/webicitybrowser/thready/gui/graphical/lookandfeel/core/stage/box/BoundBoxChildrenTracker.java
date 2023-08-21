package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.List;

public interface BoundBoxChildrenTracker {

	void addChild(Box child);
	
	List<Box> getChildren();
	
	List<Box> getAdjustedBoxTree();
	
}
