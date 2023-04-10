package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.cache;

import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.gui.tree.core.UINode;

public interface ChildComponentUICache {

	void recompute(UINode[] children, Function<Component, ComponentUI> uiGenerator);

	ComponentUI[] getChildrenUI();
	
}