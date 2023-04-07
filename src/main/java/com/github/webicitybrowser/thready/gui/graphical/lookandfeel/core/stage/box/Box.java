package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

public interface Box {

	void addChild(Box child);
	
	Box[] getAdjustedBoxTree();
	
}
