package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.basics.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.gui.tree.core.UINode;

public class ContainerContext implements Context {

	private final UIDisplay<?, ?, ?> display;
	private final ComponentUI componentUI;
	private final MappingCache<Component, ContainerChildEntry> uiMappingCache;

	private DirectivePool styleDirectives;

	public ContainerContext(
		UIDisplay<?, ?, ?> display, ComponentUI componentUI, MappingCache<Component, ContainerChildEntry> uiMappingCache
	) {
		this.display = display;
		this.componentUI = componentUI;
		this.uiMappingCache = uiMappingCache;
	}

	public UIDisplay<?, ?, ?> display() {
		return display;
	}

	public ComponentUI componentUI() {
		return componentUI;
	}

	public MappingCache<Component, ContainerChildEntry> uiMappingCache() {
		return uiMappingCache;
	}

	public Component owningComponent() {
		return componentUI.getComponent();
	}

	@Override
	public DirectivePool styleDirectives() {
		return styleDirectives;
	}
	
	@Override
	public List<Context> children() {
		List<Context> children = new ArrayList<>();
		for (ContainerChildEntry entry : uiMappingCache.getComputedMappings()) {
			children.add(entry.context());
		}

		return children;
	}

	@Override
	public void regenerateStyling(DirectivePool styleDirectives, StyleContext styleContext) {
		this.styleDirectives = styleDirectives;
		recomputeCurrentChildUIs(styleContext.lookAndFeel(), styleDirectives);
	}

	private void recomputeCurrentChildUIs(LookAndFeel lookAndFeel, DirectivePool directives) {
		List<Component> componentChildren = getComponentChildren(directives);
		uiMappingCache.recompute(componentChildren, component -> createComponentChildEntry(this, component, lookAndFeel));
	}
	
	private static ContainerChildEntry createComponentChildEntry(ContainerContext displayContext, Component component, LookAndFeel lookAndFeel) {
		ComponentUI childUI = lookAndFeel.createUIFor(component, displayContext.componentUI());
		Context childContext = childUI.getRootDisplay().createContext(childUI);
		
		return new ContainerChildEntry(childUI, childContext);
	}

	private static List<Component> getComponentChildren(DirectivePool directives) {
		List<UINode> uiChildren = directives
			.getDirectiveOrEmpty(ChildrenDirective.class)
			.map(directive -> directive.getChildren())
			.orElse(List.of());
		
		// TODO: Support other UI nodes
		List<Component> componentChildren = new ArrayList<>(uiChildren.size());
		for (UINode uiChild : uiChildren) {
			if (uiChild instanceof Component childComponent) {
				componentChildren.add(childComponent);
			}
		}

		return componentChildren;
	}
	
}
