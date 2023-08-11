package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public record ContainerContext(ComponentUI componentUI, MappingCache<Component, ContainerChildEntry> uiMappingCache) implements Context {
	
	public Component owningComponent() {
		return componentUI.getComponent();
	}
	
}
