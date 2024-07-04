package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.cache.imp.MappingCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.context.GenericContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public class ElementContext extends GenericContext {

	private final MappingCache<Component, Context> childCache = new MappingCacheImp<>(context -> context.componentUI().getComponent());
	
	public ElementContext(UIDisplay<?, ?, ElementUnit> display, ComponentUI componentUI) {
		super(display, componentUI);
	}

	@Override
	public List<Context> children() {
		return childCache.getComputedMappings();
	}

	@Override
	public void regenerateStyling(DirectivePool styleDirectives, StyleContext styleContext) {
		super.regenerateStyling(styleDirectives, styleContext);
		updateChildMapping(styleContext.lookAndFeel());
	}

	public Component component() {
		return componentUI().getComponent();
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
		ComponentUI childUI = lookAndFeel.createUIFor(component, componentUI());
		return childUI.getRootDisplay().createContext(childUI);
	}
	
}
