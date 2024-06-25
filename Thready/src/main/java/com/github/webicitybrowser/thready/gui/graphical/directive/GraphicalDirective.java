package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;

public interface GraphicalDirective extends Directive {

	InvalidationLevel getInvalidationLevel();
	
}
