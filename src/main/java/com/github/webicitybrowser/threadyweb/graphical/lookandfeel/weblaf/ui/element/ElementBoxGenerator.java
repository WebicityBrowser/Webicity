package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowInnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.OuterDisplay;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;

public class ElementBoxGenerator {

	private final BiFunction<LayoutResult, DirectivePool, RenderedUnit> anonBoxGenerator;
	private final Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator;

	public ElementBoxGenerator(
		BiFunction<LayoutResult, DirectivePool, RenderedUnit> anonBoxGenerator,
		Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator
	) {
		this.anonBoxGenerator = anonBoxGenerator;
		this.innerUnitGenerator = innerUnitGenerator;
	}
	
	public List<ChildrenBox> generateBoxes(ElementContext elementContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		DirectivePool directives = styleGenerator.getStyleDirectives();
		OuterDisplay boxDisplay = WebDirectiveUtil.getOuterDisplay(directives);
		
		ChildrenBox rootBox = createBox(elementContext, directives, boxDisplay);
		addChildrenToBox(rootBox, elementContext, boxContext, styleGenerator);
		
		return List.of(rootBox);
	}
	
	//
	
	private ChildrenBox createBox(ElementContext elementContext, DirectivePool directives, OuterDisplay boxDisplay) {
		Component component = elementContext.component();
		InnerDisplayLayout layout = getLayout(elementContext, directives);
		
		switch (boxDisplay) {
		case BLOCK:
			return new ElementBlockBox(elementContext.display(), component, directives, layout);
		case INLINE:
		default:
			return new ElementInlineBox(elementContext.display(), component, directives, layout);
		}
	}

	private InnerDisplayLayout getLayout(ElementContext elementContext, DirectivePool directives) {
		return new FlowInnerDisplayLayout(anonBoxGenerator, innerUnitGenerator);
	}
	
	// Children
	
	private static void addChildrenToBox(ChildrenBox rootBox, ElementContext elementContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		List<Box> children = getChildrenBoxes(elementContext, boxContext, styleGenerator);
		rootBox.getChildrenTracker().addAllChildren(children);
	}
	
	private static List<Box> getChildrenBoxes(ElementContext elementContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		List<Box> childrenBoxes = new ArrayList<>();
		
		Context[] pipelines = computeCurrentChildUIs(elementContext, boxContext.getLookAndFeel());
		ComponentUI[] children = getComponentUIsFromPipelines(pipelines);
		StyleGenerator[] childStyleGenerators = styleGenerator.createChildStyleGenerators(children);
		for (int i = 0; i < pipelines.length; i++) {
			childrenBoxes.addAll(UIPipeline.generateBoxes(pipelines[i], boxContext, childStyleGenerators[i]));
		}
		
		return childrenBoxes;
	}

	private static Context[] computeCurrentChildUIs(ElementContext elementContext, LookAndFeel lookAndFeel) {
		MappingCache<Component, Context> childCache = elementContext.getChildCache();
		ComponentUI parentUI = elementContext.componentUI();
		
		WebComponent[] componentChildren = elementContext.getChildren();
		childCache.recompute(
			componentChildren,
			component -> createUIContext(component, parentUI, lookAndFeel));
		
		return elementContext.getChildCache().getComputedMappings();
	}

	private static ComponentUI[] getComponentUIsFromPipelines(Context[] pipelines) {
		ComponentUI[] componentUIs = new ComponentUI[pipelines.length];
		for (int i = 0; i < pipelines.length; i++) {
			componentUIs[i] = pipelines[i].componentUI();
		}
		return componentUIs;
	}
	
	private static Context createUIContext(Component component, ComponentUI parentUI, LookAndFeel lookAndFeel) {
		ComponentUI childUI = lookAndFeel.createUIFor(component, parentUI);
		return childUI.getRootDisplay().createContext(childUI);
	}
	
}
