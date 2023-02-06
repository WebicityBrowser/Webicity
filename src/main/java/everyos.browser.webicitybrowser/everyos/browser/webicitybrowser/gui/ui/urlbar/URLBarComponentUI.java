package everyos.browser.webicitybrowser.gui.ui.urlbar;

import everyos.browser.webicitybrowser.gui.behavior.URLBarComponent;
import everyos.desktop.thready.basic.component.box.BasicSolidBox;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.laf.ComponentUI;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.BoxContext;
import everyos.desktop.thready.laf.simple.component.SimpleComponentUIBase;
import everyos.desktop.thready.laf.simple.util.SimpleBoxGenerator;

public class URLBarComponentUI extends SimpleComponentUIBase<URLBarComponent> {

	public URLBarComponentUI(Component component, ComponentUI parent) {
		super((URLBarComponent) component, parent);
	}

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		DirectivePool directives = setupComposedDirectivePool(parentDirectives, styleGenerator);
		
		return SimpleBoxGenerator.generateBoxes(directives, () -> new Box[] {
			new BasicSolidBox(
				getComponent(),
				directives,
				(box, children) -> new URLBarComponentRenderer(box, getComponent()))
		});
	}

}
