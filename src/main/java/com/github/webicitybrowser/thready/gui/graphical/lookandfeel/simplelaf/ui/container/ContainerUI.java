package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

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
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.gui.tree.core.UINode;

public class ContainerUI implements ComponentUI {

	private final Component component;
	
	private final ChildComponentUICache childCache = new ChildComponentUICacheImp();

	public ContainerUI(Component component, ComponentUI parent) {
		this.component = component;
	}
	
	@Override
	public Component getComponent() {
		return this.component;
	}
	
	@Override
	public Box[] generateBoxes(BoxContext context) {
		return SimpleBoxGenerator.generateBoxes(() -> performBoxing(context));
	}

	private Box[] performBoxing(BoxContext context) {
		DirectivePool directives = component.getStyleDirectives();
		Box rootBox = new BasicSolidBox(
			directives,
			(box, children) -> createRenderer(box, children));
		
		ComponentUI[] children = computeCurrentChildren(context.getLookAndFeel(), directives);
		for (int i = 0; i < children.length; i++) {
			addChildBoxes(rootBox, children[i], context, directives);
		}
		
		return new Box[] { rootBox };
	}
	
	private SolidRenderer createRenderer(Box box, Box[] children) {
		return new ContainerRenderer(box, children);
	}
	
	private ComponentUI[] computeCurrentChildren(LookAndFeel lookAndFeel, DirectivePool directives) {
		UINode[] componentChildren = directives
			.getDirectiveOrEmpty(ChildrenDirective.class)
			.map(directive -> directive.getChildren())
			.orElse(new UINode[0]);
		
		
		childCache.recompute(componentChildren, component -> lookAndFeel.createUIFor(component, this));
		
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
