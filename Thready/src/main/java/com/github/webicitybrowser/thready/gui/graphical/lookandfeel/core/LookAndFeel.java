package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core;

import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface LookAndFeel {

	ComponentUI createUIFor(Component component, ComponentUI parent);
	
}
