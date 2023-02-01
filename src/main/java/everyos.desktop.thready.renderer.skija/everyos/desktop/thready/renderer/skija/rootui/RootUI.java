package everyos.desktop.thready.renderer.skija.rootui;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.ComposedDirectivePool;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.BoxContext;

public abstract class RootUI implements ComponentUI {

	@Override
	public ComposedDirectivePool<?> getComputedDirectives() {
		return null;
	}

	@Override
	public Box[] generateBoxes(BoxContext context) {
		return null;
	}
	
	@Override
	public Component getComponent() {
		return null;
	}

}
