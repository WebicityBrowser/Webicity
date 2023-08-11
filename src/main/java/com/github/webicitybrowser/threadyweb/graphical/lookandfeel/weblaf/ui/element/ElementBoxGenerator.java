package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.PipelinedContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective.OuterDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowInnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;

public class ElementBoxGenerator {
	
	public static List<ChildrenBox> generateBoxes(ElementContext elementContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		DirectivePool directives = styleGenerator.getStyleDirectives();
		OuterDisplay boxDisplay = WebDirectiveUtil.getOuterDisplay(directives);
		/*if (boxDisplay == OuterDisplay.CONTENTS) {
			return generateContentsBoxes(elementContext, boxContext, styleGenerator);
		}*/
		
		ChildrenBox rootBox = createBox(elementContext, directives, boxDisplay);
		addChildrenToBox(rootBox, elementContext, boxContext, styleGenerator);
		
		return List.of(rootBox);
	}
	
	//
	
	private static ChildrenBox createBox(ElementContext elementContext, DirectivePool directives, OuterDisplay boxDisplay) {
		Component component = elementContext.component();
		InnerDisplayLayout layout = getLayout(directives);
		
		switch (boxDisplay) {
		case BLOCK:
			return new ElementBlockBox(component, directives, layout);
		case INLINE:
		default:
			return new ElementInlineBox(component, directives, layout);
		}
	}

	private static InnerDisplayLayout getLayout(DirectivePool directives) {
		return new FlowInnerDisplayLayout();
	}
	
	// Children
	
	private static void addChildrenToBox(ChildrenBox rootBox, ElementContext elementContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		for (BoundBox<?, ?> box: getChildrenBoxes(elementContext, boxContext, styleGenerator)) {
			rootBox.getChildrenTracker().addChild(box);
		}
	}
	
	private static List<BoundBox<?, ?>> getChildrenBoxes(ElementContext elementContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		List<BoundBox<?, ?>> childrenBoxes = new ArrayList<>();
		
		PipelinedContext<?, ?, ?>[] pipelines = computeCurrentChildUIs(elementContext, boxContext.getLookAndFeel());
		ComponentUI[] children = getComponentUIsFromPipelines(pipelines);
		StyleGenerator[] childStyleGenerators = styleGenerator.createChildStyleGenerators(children);
		for (int i = 0; i < pipelines.length; i++) {
			List<BoundBox<?, ?>> childBoxes = getChildBoxes(pipelines[i], boxContext, childStyleGenerators[i]);
			childrenBoxes.addAll(childBoxes);
		}
		
		return childrenBoxes;
	}

	private static ComponentUI[] getComponentUIsFromPipelines(PipelinedContext<?, ?, ?>[] pipelines) {
		ComponentUI[] componentUIs = new ComponentUI[pipelines.length];
		for (int i = 0; i < pipelines.length; i++) {
			componentUIs[i] = pipelines[i].getRaw().componentUI();
		}
		return componentUIs;
	}

	private static PipelinedContext<?, ?, ?>[] computeCurrentChildUIs(ElementContext elementContext, LookAndFeel lookAndFeel) {
		MappingCache<Component, PipelinedContext<?, ?, ?>> childCache = elementContext.getChildCache();
		ComponentUI parentUI = elementContext.componentUI();
		
		WebComponent[] componentChildren = elementContext.getChildren();
		childCache.recompute(
			componentChildren,
			component -> createUIContext(component, parentUI, lookAndFeel));
		
		return elementContext.getChildCache().getComputedMappings();
	}
	
	private static PipelinedContext<?, ?, ?> createUIContext(Component component, ComponentUI parentUI, LookAndFeel lookAndFeel) {
		ComponentUI child = lookAndFeel.createUIFor(component, parentUI);
		return UIPipeline.create(child.getRootDisplay()).createContext(child);
	}

	private static List<BoundBox<?, ?>> getChildBoxes(
		PipelinedContext<?, ?, ?> child, BoxContext context, StyleGenerator styleGenerator
	) {
		List<BoundBox<?, ?>> childBoxes = new ArrayList<>();
		for (BoundBox<?, ?> box: child.generateBoxes(context, styleGenerator)) {
			for (BoundBox<?, ?> adjustedBox: box.getAdjustedBoxTree()) {
				childBoxes.add(adjustedBox);
			}
		};
		
		return childBoxes;
	}
	
}
