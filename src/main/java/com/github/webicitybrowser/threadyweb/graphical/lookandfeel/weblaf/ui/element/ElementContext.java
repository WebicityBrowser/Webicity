package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.cache.imp.MappingCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;

public class ElementContext implements Context {

	private final MappingCache<Component, Context> childCache = new MappingCacheImp<>(
		Context[]::new,
		context -> context.componentUI().getComponent());
	
	private final UIDisplay<?, ?, InnerDisplayUnit> display;
	private final ComponentUI componentUI;

	public ElementContext(UIDisplay<?, ?, InnerDisplayUnit> display, ComponentUI componentUI) {
		this.display = display;
		this.componentUI = componentUI;
	}

	@Override
	public UIDisplay<?, ?, InnerDisplayUnit> display() {
		return this.display;
	}
	
	@Override
	public ComponentUI componentUI() {
		return this.componentUI;
	}
	
	public Component component() {
		return componentUI.getComponent();
	}
	
	public MappingCache<Component, Context> getChildCache() {
		return this.childCache;
	}

	public WebComponent[] getChildren() {
		return ((ElementComponent) component()).getChildren();
	}
	
}
