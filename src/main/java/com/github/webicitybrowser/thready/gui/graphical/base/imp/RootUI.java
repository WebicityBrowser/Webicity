package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public abstract class RootUI implements ComponentUI {

	public Component getComponent() {
		throw new UnsupportedOperationException();
	}
	
	public Box[] generateBoxes(BoxContext context) {
		throw new UnsupportedOperationException();
	};
	
}