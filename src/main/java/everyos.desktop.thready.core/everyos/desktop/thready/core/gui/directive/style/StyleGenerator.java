package everyos.desktop.thready.core.gui.directive.style;

import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.laf.ComponentUI;

public interface StyleGenerator {

	StyleGenerator[] createChildStyleGenerators(ComponentUI[] children);
	
	DirectivePool[] getDirectivePools();
	
	// TODO: Special styles
	// TODO: Listener for invalidation
	
}
