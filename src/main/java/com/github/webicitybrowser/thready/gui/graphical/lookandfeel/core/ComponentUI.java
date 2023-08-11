package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface ComponentUI {
	
	Component getComponent();
	
	void invalidate(InvalidationLevel level);
	
	UIDisplay<?, ?, ?> getRootDisplay();
	
}
