package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;

public interface Box {

	void addChild(Box child);
	
	Box[] getAdjustedBoxTree();

	DirectivePool getStyleDirectives();
	
	Renderer createRenderer();
	
}
