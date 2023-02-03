package everyos.desktop.thready.laf.simple.component.ui.container;

import everyos.desktop.thready.basic.component.box.BasicSolidBox;
import everyos.desktop.thready.basic.directive.ChildrenDirective;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.laf.ComponentUI;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.BoxContext;
import everyos.desktop.thready.core.gui.stage.render.SolidRenderer;
import everyos.desktop.thready.laf.simple.component.SimpleComponentUIBase;
import everyos.desktop.thready.laf.simple.component.cache.ChildComponentUICache;
import everyos.desktop.thready.laf.simple.component.cache.imp.ChildComponentUICacheImp;
import everyos.desktop.thready.laf.simple.util.SimpleBoxGenerator;

public class SimpleContainerComponentUI extends SimpleComponentUIBase<Component> {
	
	private final ChildComponentUICache childCache = new ChildComponentUICacheImp();

	public SimpleContainerComponentUI(Component component, ComponentUI parent) {
		super(component, parent);
	}

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator generator) {
		DirectivePool directives = setupComposedDirectivePool(parentDirectives, generator);
		return SimpleBoxGenerator.generateBoxes(directives, () -> performBoxing(context, directives, generator));
	}

	private Box[] performBoxing(BoxContext context, DirectivePool directives, StyleGenerator generator) {
		Box rootBox = new BasicSolidBox(
			getComponent(),
			directives,
			(box, children) -> createRenderer(box, children));
		
		ComponentUI[] children = getChildren(context.getLookAndFeel(), directives);
		StyleGenerator[] childGenerators = generator.createChildStyleGenerators(children);
		for (int i = 0; i < children.length; i++) {
			addChildBoxes(rootBox, children[i], context, directives, childGenerators[i]);
		}
		
		return new Box[] { rootBox };
	}
	
	private SolidRenderer createRenderer(Box box, Box[] children) {
		return new ContainerComponentRenderer(box, children);
	}

	private ComponentUI[] getChildren(LookAndFeel lookAndFeel, DirectivePool directives) {
		Component[] componentChildren = directives
			.getDirectiveOrEmpty(ChildrenDirective.class)
			.map(directive -> directive.getChildren())
			.orElse(new Component[0]);
		
		childCache.recompute(componentChildren, component -> lookAndFeel.createUIFor(component, this));
		
		return childCache.getChildrenUI();
	}
	
	private void addChildBoxes(
		Box rootBox, ComponentUI child, BoxContext context, DirectivePool directives, StyleGenerator generator
	) {
		for (Box box: child.generateBoxes(context, directives, generator)) {
			for (Box adjustedBox: box.getAdjustedBoxTree()) {
				rootBox.addChild(adjustedBox);
			}
		};
	}

}
