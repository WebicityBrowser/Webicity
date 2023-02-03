package everyos.browser.webicitybrowser.gui.ui.button;

import everyos.browser.webicitybrowser.component.CircularButtonComponent;
import everyos.desktop.thready.basic.component.box.BasicSolidBox;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.laf.ComponentUI;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.BoxContext;
import everyos.desktop.thready.laf.simple.component.SimpleComponentUIBase;
import everyos.desktop.thready.laf.simple.util.SimpleBoxGenerator;

public class CircularButtonComponentUI extends SimpleComponentUIBase<CircularButtonComponent> {

	public CircularButtonComponentUI(Component component, ComponentUI parent) {
		super((CircularButtonComponent) component, parent);
	}

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		DirectivePool directives = setupComposedDirectivePool(parentDirectives, styleGenerator);
		
		return SimpleBoxGenerator.generateBoxes(directives, () -> new Box[] {
			new BasicSolidBox(
				getComponent(),
				directives,
				(box, children) -> new CircularButtonComponentRenderer(box))
		});
	}

}
