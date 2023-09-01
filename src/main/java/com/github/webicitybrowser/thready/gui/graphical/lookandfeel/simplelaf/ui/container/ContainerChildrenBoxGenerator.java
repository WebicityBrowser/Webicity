package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import java.util.Arrays;
import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.basics.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.gui.tree.core.UINode;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebBoxGeneratorUtil;

public final class ContainerChildrenBoxGenerator {
	
	private ContainerChildrenBoxGenerator() {}
	
	public static void addChildrenBoxes(
		ContainerContext displayContext, ChildrenBox rootBox, BoxContext boxContext, StyleGenerator styleGenerator
	) {
		DirectivePool directives = styleGenerator.getStyleDirectives();
		ContainerChildEntry[] children = computeCurrentChildUIs(displayContext, boxContext.getLookAndFeel(), directives);
		StyleGenerator[] childStyleGenerators = createChildStyleGenerators(styleGenerator, children);
		for (int i = 0; i < children.length; i++) {
			List<Box> boxesToAdd = WebBoxGeneratorUtil.generateWebBoxes(children[i].context(), boxContext, childStyleGenerators[i]);
			rootBox.getChildrenTracker().addAllChildren(boxesToAdd);
		}
	}

	private static StyleGenerator[] createChildStyleGenerators(StyleGenerator styleGenerator, ContainerChildEntry[] childEntries) {
		ComponentUI[] children = new ComponentUI[childEntries.length];
		for (int i = 0; i < childEntries.length; i++) {
			children[i] = childEntries[i].componentUI();
		}
		
		return styleGenerator.createChildStyleGenerators(children);
	}

	private static ContainerChildEntry[] computeCurrentChildUIs(ContainerContext displayContext, LookAndFeel lookAndFeel, DirectivePool directives) {
		Component[] componentChildren = getComponentChildren(directives);
		MappingCache<Component, ContainerChildEntry> childCache = displayContext.uiMappingCache();
		childCache.recompute(componentChildren, component -> createComponentChildEntry(displayContext, component, lookAndFeel));
		
		return childCache.getComputedMappings();
	}
	
	private static ContainerChildEntry createComponentChildEntry(ContainerContext displayContext, Component component, LookAndFeel lookAndFeel) {
		ComponentUI childUI = lookAndFeel.createUIFor(component, displayContext.componentUI());
		Context childContext = childUI.getRootDisplay().createContext(childUI);
		
		return new ContainerChildEntry(childUI, childContext);
	}

	private static Component[] getComponentChildren(DirectivePool directives) {
		UINode[] uiChildren = directives
			.getDirectiveOrEmpty(ChildrenDirective.class)
			.map(directive -> directive.getChildren())
			.orElse(new UINode[0]);
		
		// TODO: Support other UI nodes
		return Arrays.copyOf(uiChildren, uiChildren.length, Component[].class);
	}
	
}
