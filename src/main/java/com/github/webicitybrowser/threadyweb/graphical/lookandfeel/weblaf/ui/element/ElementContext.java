package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.cache.imp.MappingCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.ChildrenContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;

public class ElementContext implements ChildrenContext {

	private final MappingCache<Component, Context> childCache = new MappingCacheImp<>(
		Context[]::new,
		context -> context.componentUI().getComponent()
	);
	
	private final UIDisplay<?, ?, ElementUnit> display;
	private final ComponentUI componentUI;

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
	public Context[] children(LookAndFeel lookAndFeel) {
		updateChildMapping(lookAndFeel);

		return childCache.getComputedMappings();
	}

	@Override
	public StyleGenerator[] childrenStyleGenerators(StyleGenerator styleGenerator, Context[] contexts) {
		ComponentUI[] childUIs = new ComponentUI[contexts.length];
		for (int i = 0; i < contexts.length; i++) {
			childUIs[i] = contexts[i].componentUI();
		}
		return styleGenerator.createChildStyleGenerators(childUIs);
	}

	public Component component() {
		return componentUI.getComponent();
	}

	private void updateChildMapping(LookAndFeel lookAndFeel) {
		childCache.recompute(
			getChildren(),
			component -> createUIContext(component, lookAndFeel));
	}

	private WebComponent[] getChildren() {
		return ((ElementComponent) component()).getChildren();
	}

	private Context createUIContext(Component component, LookAndFeel lookAndFeel) {
		ComponentUI childUI = lookAndFeel.createUIFor(component, componentUI);
		return childUI.getRootDisplay().createContext(childUI);
	}
	
}
