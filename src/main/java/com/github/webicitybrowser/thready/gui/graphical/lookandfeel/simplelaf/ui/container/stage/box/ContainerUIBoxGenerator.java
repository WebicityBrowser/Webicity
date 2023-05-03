package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.box;

import java.util.function.BiFunction;

import com.github.webicitybrowser.thready.gui.directive.basics.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.cache.imp.MappingCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleBoxGenerator;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.gui.tree.core.UINode;

public class ContainerUIBoxGenerator {
	
	private final ComponentUI parentUI;
	private final BiFunction<Box, Box[], Renderer> rendererGenerator;
	private final MappingCache<Component, ComponentUI> childCache = new MappingCacheImp<>(ComponentUI[]::new, ui -> ui.getComponent());

	public ContainerUIBoxGenerator(ComponentUI parentUI, BiFunction<Box, Box[], Renderer> rendererGenerator) {
		this.parentUI = parentUI;
		this.rendererGenerator = rendererGenerator;
	}
	
	public Box[] generateBoxes(BoxContext context, DirectivePool directives, StyleGenerator styleGenerator) {
		return SimpleBoxGenerator.generateBoxes(() -> generateRootBox(context, directives, styleGenerator));
	}
	
	//
	
	private Box[] generateRootBox(BoxContext context, DirectivePool directives, StyleGenerator styleGenerator) {
		Box rootBox = new BasicBox(parentUI.getComponent(), directives, rendererGenerator);
		addChildrenBoxes(rootBox, context, directives, styleGenerator);
		
		return new Box[] { rootBox };
	}
	
	private void addChildrenBoxes(Box rootBox, BoxContext context, DirectivePool directives, StyleGenerator styleGenerator) {
		ComponentUI[] children = computeCurrentChildUIs(context.getLookAndFeel(), directives);
		StyleGenerator[] childStyleGenerators = styleGenerator.createChildStyleGenerators(children);
		for (int i = 0; i < children.length; i++) {
			addChildBoxes(rootBox, children[i], context, directives, childStyleGenerators[i]);
		}
	}

	private ComponentUI[] computeCurrentChildUIs(LookAndFeel lookAndFeel, DirectivePool directives) {
		Component[] componentChildren = getComponentChildren(directives);
		childCache.recompute(componentChildren, component -> lookAndFeel.createUIFor(component, parentUI));
		
		return childCache.getComputedMappings();
	}
	
	private Component[] getComponentChildren(DirectivePool directives) {
		UINode[] uiChildren = directives
			.getDirectiveOrEmpty(ChildrenDirective.class)
			.map(directive -> directive.getChildren())
			.orElse(new UINode[0]);
		
		// TODO: Support other UI nodes
		Component[] components = new Component[uiChildren.length];
		System.arraycopy(uiChildren, 0, components, 0, uiChildren.length);
		
		return components;
	}

	private void addChildBoxes(
		Box rootBox, ComponentUI child, BoxContext context, DirectivePool directives, StyleGenerator styleGenerator
	) {
		for (Box box: child.generateBoxes(context, directives, styleGenerator)) {
			for (Box adjustedBox: box.getAdjustedBoxTree()) {
				rootBox.addChild(adjustedBox);
			}
		};
	}
	
}
