package everyos.desktop.thready.renderer.skija.rootui;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.laf.ComponentUI;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.BoxContext;

public abstract class RootUI implements ComponentUI {

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator generator) {
		return null;
	}
	
	@Override
	public Component getComponent() {
		return null;
	}
	
	@Override
	public void release() {}

}
