package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.box;

import java.util.function.BiFunction;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.directive.directive.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicSolidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.SolidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.cache.ChildComponentUICache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.cache.imp.ChildComponentUICacheImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleBoxGenerator;
import com.github.webicitybrowser.thready.gui.tree.core.UINode;

public class ContainerUIBoxGenerator {
	
	private final ComponentUI parentUI;
	private final BiFunction<Box, Box[], SolidRenderer> rendererGenerator;
	private final ChildComponentUICache childCache = new ChildComponentUICacheImp();

	public ContainerUIBoxGenerator(ComponentUI parentUI, BiFunction<Box, Box[], SolidRenderer> rendererGenerator) {
		this.parentUI = parentUI;
		this.rendererGenerator = rendererGenerator;
	}
	
	public Box[] generateBoxes(BoxContext context, DirectivePool directives) {
		return SimpleBoxGenerator.generateBoxes(() -> performBoxing(context, directives));
	}
	
	//
	
	private Box[] performBoxing(BoxContext context, DirectivePool directives) {
		Box rootBox = new BasicSolidBox(directives, rendererGenerator);
		
		ComponentUI[] children = computeCurrentChildUIs(context.getLookAndFeel(), directives);
		for (int i = 0; i < children.length; i++) {
			addChildBoxes(rootBox, children[i], context, directives);
		}
		
		return new Box[] { rootBox };
	}
	
	private ComponentUI[] computeCurrentChildUIs(LookAndFeel lookAndFeel, DirectivePool directives) {
		UINode[] componentChildren = directives
			.getDirectiveOrEmpty(ChildrenDirective.class)
			.map(directive -> directive.getChildren())
			.orElse(new UINode[0]);
		
		childCache.recompute(componentChildren, component -> lookAndFeel.createUIFor(component, parentUI));
		
		return childCache.getChildrenUI();
	}
	
	private void addChildBoxes(
		Box rootBox, ComponentUI child, BoxContext context, DirectivePool directives
	) {
		for (Box box: child.generateBoxes(context)) {
			for (Box adjustedBox: box.getAdjustedBoxTree()) {
				rootBox.addChild(adjustedBox);
			}
		};
	}
	
}
