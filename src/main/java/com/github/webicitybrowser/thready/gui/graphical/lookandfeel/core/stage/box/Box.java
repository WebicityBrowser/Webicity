package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface Box {
	
	Component getOwningComponent();

	void addChild(Box child);
	
	Box[] getAdjustedBoxTree();

	DirectivePool getStyleDirectives();
	
	Renderer createRenderer();
	
}
