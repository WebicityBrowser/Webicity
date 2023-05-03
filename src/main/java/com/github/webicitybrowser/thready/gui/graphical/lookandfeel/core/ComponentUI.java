package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface ComponentUI {

	Component getComponent();
	
	void invalidate(InvalidationLevel level);
	
	Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator);
	
}
