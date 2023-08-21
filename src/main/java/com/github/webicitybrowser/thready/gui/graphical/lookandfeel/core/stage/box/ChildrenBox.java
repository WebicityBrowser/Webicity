package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.List;

public interface ChildrenBox extends Box {

	BoxChildrenTracker getChildrenTracker();
	
	@Override
	default List<Box> getAdjustedBoxTree() {
		return getChildrenTracker().getAdjustedBoxTree();
	};
	
}
