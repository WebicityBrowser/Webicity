package everyos.desktop.thready.core.gui.laf.component;

import everyos.desktop.thready.core.gui.directive.ComposedDirectivePool;
import everyos.desktop.thready.core.gui.laf.component.box.Box;

public interface ComponentUI {
	
	// Note: For performance reasons, we should probably
	// should avoid anything that reads from a HashMap during
	// the render pipeline. I'll need to do some testing to
	// decide.
	ComposedDirectivePool getComputedDirectives();

	Box[] getBoxes();
	
}
