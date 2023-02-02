package everyos.desktop.thready.laf.simple.component.ui.container;

import everyos.desktop.thready.basic.component.box.BasicSolidBox;
import everyos.desktop.thready.basic.directive.ChildrenDirective;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;
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
	public Box[] generateBoxes(BoxContext context) {
		return SimpleBoxGenerator.generateBoxes(getComputedDirectives(), () -> performBoxing(context));
	}

	private Box[] performBoxing(BoxContext context) {
		Box rootBox = new BasicSolidBox(
			getComponent(),
			(box, children) -> createRenderer(box, children));
		
		for (ComponentUI child: getChildren(context.getLookAndFeel())) {
			addChildBoxes(rootBox, child, context);
		}
		
		return new Box[] { rootBox };
	}
	
	private SolidRenderer createRenderer(Box box, Box[] children) {
		return new ContainerComponentRenderer(box, children);
	}

	private ComponentUI[] getChildren(LookAndFeel lookAndFeel) {
		Component[] componentChildren = getComputedDirectives()
			.getDirectiveOrEmpty(ChildrenDirective.class)
			.map(directive -> directive.getChildren())
			.orElse(new Component[0]);
		
		childCache.recompute(componentChildren, component -> lookAndFeel.createUIFor(component, this));
		
		return childCache.getChildrenUI();
	}
	
	private void addChildBoxes(Box rootBox, ComponentUI child, BoxContext context) {
		for (Box box: child.generateBoxes(context)) {
			for (Box adjustedBox: box.getAdjustedBoxTree()) {
				rootBox.addChild(adjustedBox);
			}
		};
	}

}
