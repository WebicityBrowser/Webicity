package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.box;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.cache.imp.MappingCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective.OuterDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.flow.FlowInnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebBoxGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;

public class ElementUIBoxGenerator {
	
	private final ElementComponent component;
	private final ComponentUI parentUI;
	private final MappingCache<Component, ComponentUI> childCache = new MappingCacheImp<>(ComponentUI[]::new, ui -> ui.getComponent());

	public ElementUIBoxGenerator(ElementComponent component, ComponentUI parentUI) {
		this.component = component;
		this.parentUI = parentUI;
	}
	
	public Box[] generateBoxes(BoxContext context, DirectivePool directives, StyleGenerator styleGenerator) {
		return WebBoxGenerator.generateBoxes(directives, () -> performBoxing(context, directives, styleGenerator));
	}
	
	//
	
	private Box[] performBoxing(BoxContext context, DirectivePool directives, StyleGenerator styleGenerator) {
		OuterDisplay boxDisplay = WebDirectiveUtil.getOuterDisplay(directives);
		if (boxDisplay == OuterDisplay.CONTENTS) {
			return generateContentsBoxes(context, directives, styleGenerator);
		}
		
		Box rootBox = createBox(directives, boxDisplay);
		addChildrenToBox(rootBox, context, directives, styleGenerator);
		
		return new Box[] { rootBox };
	}
	
	private Box createBox(DirectivePool directives, OuterDisplay boxDisplay) {
		InnerDisplayLayout layout = getLayout(directives);
		
		switch (boxDisplay) {
		case BLOCK:
			return generateBlockRootBox(directives, layout);
		case INLINE:
		default:
			return generateInlineRootBox(directives, layout);
		}
	}

	private InnerDisplayLayout getLayout(DirectivePool directives) {
		return new FlowInnerDisplayLayout();
	}

	// Display types
	
	private Box[] generateContentsBoxes(BoxContext context, DirectivePool directives, StyleGenerator styleGenerator) {
		return getChildrenBoxes(context, directives, styleGenerator).toArray(Box[]::new);
	}
	
	
	private Box generateBlockRootBox(DirectivePool directives, InnerDisplayLayout layout) {
		Box rootBox = new BasicBox(
			component, directives,
			(box, children) -> layout.createRenderer(box, children));
		
		return rootBox;
	}
	
	
	private Box generateInlineRootBox(DirectivePool directives, InnerDisplayLayout layout) {
		Box rootBox = new BasicFluidBox(
			component, directives,
			(box, children) -> layout.createRenderer(box, children));
		
		return rootBox;
	}
	
	// Children
	
	private void addChildrenToBox(Box rootBox, BoxContext context, DirectivePool directives, StyleGenerator styleGenerator) {
		for (Box box: getChildrenBoxes(context, directives, styleGenerator)) {
			rootBox.addChild(box);
		}
	}
	
	private List<Box> getChildrenBoxes(BoxContext context, DirectivePool directives, StyleGenerator styleGenerator) {
		List<Box> childrenBoxes = new ArrayList<>();
		
		ComponentUI[] children = computeCurrentChildUIs(context.getLookAndFeel(), directives);
		StyleGenerator[] childStyleGenerators = styleGenerator.createChildStyleGenerators(children);
		for (int i = 0; i < children.length; i++) {
			List<Box> childBoxes = getChildBoxes(children[i], context, directives, childStyleGenerators[i]);
			childrenBoxes.addAll(childBoxes);
		}
		
		return childrenBoxes;
	}

	private ComponentUI[] computeCurrentChildUIs(LookAndFeel lookAndFeel, DirectivePool directives) {
		WebComponent[] componentChildren = component.getChildren();
		childCache.recompute(componentChildren, component -> lookAndFeel.createUIFor(component, parentUI));
		
		return childCache.getComputedMappings();
	}
	
	private List<Box> getChildBoxes(
		ComponentUI child, BoxContext context, DirectivePool directives, StyleGenerator styleGenerator
	) {
		List<Box> childBoxes = new ArrayList<>();
		for (Box box: child.generateBoxes(context, directives, styleGenerator)) {
			for (Box adjustedBox: box.getAdjustedBoxTree()) {
				childBoxes.add(adjustedBox);
			}
		};
		
		return childBoxes;
	}
	
}
