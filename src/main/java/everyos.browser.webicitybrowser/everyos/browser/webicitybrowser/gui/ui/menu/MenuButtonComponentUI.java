package everyos.browser.webicitybrowser.gui.ui.menu;

import everyos.browser.webicitybrowser.component.MenuButtonComponent;
import everyos.desktop.thready.basic.component.box.BasicSolidBox;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.laf.ComponentUI;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.BoxContext;
import everyos.desktop.thready.laf.simple.component.SimpleComponentUIBase;
import everyos.desktop.thready.laf.simple.util.SimpleBoxGenerator;

public class MenuButtonComponentUI extends SimpleComponentUIBase<MenuButtonComponent> {

	public MenuButtonComponentUI(Component component, ComponentUI parent) {
		super((MenuButtonComponent) component, parent);
	}

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		DirectivePool directives = setupComposedDirectivePool(parentDirectives, styleGenerator);
		
		return SimpleBoxGenerator.generateBoxes(directives, () -> new Box[] {
			new BasicSolidBox(
				getComponent(),
				directives,
				(box, children) -> new MenuButtonComponentRenderer(box))
		});
	}

}
