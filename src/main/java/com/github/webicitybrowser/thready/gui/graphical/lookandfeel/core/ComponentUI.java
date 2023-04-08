package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface ComponentUI {

	Component getComponent();
	
	Box[] generateBoxes(BoxContext context);
	
}
