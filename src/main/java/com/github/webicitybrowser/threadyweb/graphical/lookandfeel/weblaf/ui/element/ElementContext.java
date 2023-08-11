package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.cache.imp.MappingCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.PipelinedContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;

public class ElementContext implements Context {

	private final MappingCache<Component, PipelinedContext<?, ?, ?>> childCache = new MappingCacheImp<>(
		PipelinedContext[]::new,
		context -> context.getRaw().componentUI().getComponent());
	
	private final ComponentUI componentUI;

	public ElementContext(ComponentUI componentUI) {
		this.componentUI = componentUI;
	}
	
	public ComponentUI componentUI() {
		return this.componentUI;
	}
	
	public Component component() {
		return componentUI.getComponent();
	}
	
	public MappingCache<Component, PipelinedContext<?, ?, ?>> getChildCache() {
		return this.childCache;
	}

	public WebComponent[] getChildren() {
		return ((ElementComponent) component()).getChildren();
	}
	
}
