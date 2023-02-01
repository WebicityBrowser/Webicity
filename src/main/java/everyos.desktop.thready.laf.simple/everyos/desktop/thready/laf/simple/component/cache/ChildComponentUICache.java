package everyos.desktop.thready.laf.simple.component.cache;

import java.util.function.Function;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;

public interface ChildComponentUICache {

	void recompute(Component[] children, Function<Component, ComponentUI> uiGenerator);

	ComponentUI[] getChildrenUI();
	
}
