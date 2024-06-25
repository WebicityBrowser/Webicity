package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.cache.imp.MappingCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public class ElementContext implements Context {

	private final MappingCache<Component, Context> childCache = new MappingCacheImp<>(context -> context.componentUI().getComponent());
	
	private final UIDisplay<?, ?, ElementUnit> display;
	private final ComponentUI componentUI;

	private DirectivePool styleDirectives;

	public ElementContext(UIDisplay<?, ?, ElementUnit> display, ComponentUI componentUI) {
		this.display = display;
		this.componentUI = componentUI;
	}

	@Override
	public UIDisplay<?, ?, ElementUnit> display() {
		return this.display;
	}
	
	@Override
	public ComponentUI componentUI() {
		return this.componentUI;
	}

	@Override
	public List<Context> children() {
		return childCache.getComputedMappings();
	}
	
	@Override
	public DirectivePool styleDirectives() {
		return styleDirectives;
	}

	@Override
	public void regenerateStyling(DirectivePool styleDirectives, StyleContext styleContext) {
		this.styleDirectives = styleDirectives;
		updateChildMapping(styleContext.lookAndFeel());
	}

	public Component component() {
		return componentUI.getComponent();
	}

	private void updateChildMapping(LookAndFeel lookAndFeel) {
		childCache.recompute(
			getChildren(),
			component -> createUIContext(component, lookAndFeel));
	}

	private List<Component> getChildren() {
		return ((ElementComponent) component()).getChildren();
	}

	private Context createUIContext(Component component, LookAndFeel lookAndFeel) {
		ComponentUI childUI = lookAndFeel.createUIFor(component, componentUI);
		return childUI.getRootDisplay().createContext(childUI);
	}
	
}
