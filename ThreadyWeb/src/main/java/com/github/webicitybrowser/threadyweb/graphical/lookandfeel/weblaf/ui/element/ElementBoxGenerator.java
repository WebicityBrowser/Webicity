package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.FlexInnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowConfig;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowInnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebBoxGeneratorUtil;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.InnerDisplay;
import com.github.webicitybrowser.threadyweb.graphical.value.OuterDisplay;

public class ElementBoxGenerator {

	private final FlowConfig flowConfig;

	public ElementBoxGenerator(FlowConfig flowConfig) {
		this.flowConfig = flowConfig;
	}
	
	public List<ChildrenBox> generateBoxes(ElementContext elementContext, BoxContext boxContext) {
		ChildrenBox rootBox = createBox(elementContext, elementContext.styleDirectives());
		addChildrenToBox(rootBox, elementContext, boxContext);
		
		return List.of(rootBox);
	}
	
	//
	
	private ChildrenBox createBox(ElementContext elementContext, DirectivePool directives) {
		ComponentUI componentUI = elementContext.componentUI();
		SolidLayoutManager layout = getLayout(elementContext, directives);
		
		OuterDisplay outerDisplay = WebDirectiveUtil.getOuterDisplay(directives);
		switch (outerDisplay) {
		case BLOCK:
			return new ElementBlockBox(elementContext.display(), componentUI, directives, layout);
		case INLINE:
		default:
			return new ElementInlineBox(elementContext.display(), componentUI, directives, layout);
		}
	}

	private SolidLayoutManager getLayout(ElementContext elementContext, DirectivePool directives) {
		InnerDisplay innerDisplay = WebDirectiveUtil.getInnerDisplay(directives);
		return switch (innerDisplay) {
			case FLEX -> new FlexInnerDisplayLayout(flowConfig.styledUnitGenerator());
			default -> new FlowInnerDisplayLayout(flowConfig);
		};
	}
	
	// Children
	
	private static void addChildrenToBox(ChildrenBox rootBox, ElementContext elementContext, BoxContext boxContext) {
		List<Box> children = getChildrenBoxes(elementContext, boxContext);
		rootBox.getChildrenTracker().addAllChildren(children);
	}
	
	private static List<Box> getChildrenBoxes(ElementContext elementContext, BoxContext boxContext) {
		List<Box> childrenBoxes = new ArrayList<>();
		
		for (Context child : elementContext.children()) {
			childrenBoxes.addAll(WebBoxGeneratorUtil.generateWebBoxes(child, boxContext));
		}
		
		return childrenBoxes;
	}
	
}
