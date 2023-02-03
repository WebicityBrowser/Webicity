package everyos.desktop.thready.core.gui.laf;

import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.BoxContext;

public interface ComponentUI {
	
	void invalidate(InvalidationLevel level);

	Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator);

	Component getComponent();

	void release();
	
}
