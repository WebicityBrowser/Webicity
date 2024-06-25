package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.List;

public interface BoxChildrenTracker {

	void addChild(Box child);
	
	List<Box> getChildren();
	
	List<Box> getAdjustedBoxTree();

	 default void addAllChildren(List<Box> children) {
		for (Box child: children) {
			addChild(child);
		}
	}
	
}
