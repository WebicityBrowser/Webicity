package everyos.desktop.thready.renderer.skija.rootui;

import everyos.desktop.thready.core.gui.directive.ComposedDirectivePool;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;
import everyos.desktop.thready.core.gui.laf.component.box.Box;

public abstract class RootUI implements ComponentUI {

	@Override
	public ComposedDirectivePool<?> getComputedDirectives() {
		return null;
	}

	@Override
	public Box[] getBoxes() {
		return null;
	}

}
