package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;

public interface Box {

	void addChild(Box child);
	
	Box[] getAdjustedBoxTree();

	DirectivePool getStyleDirectives();
	
}
