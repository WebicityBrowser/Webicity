package com.github.webicitybrowser.thready.gui.directive.core.style;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;

public interface StyleGenerator {

	StyleGenerator[] createChildStyleGenerators(ComponentUI[] children);
	
	DirectivePool getStyleDirectives();
	
	// TODO: Special styles
	// TODO: Listener for invalidation
	
}
