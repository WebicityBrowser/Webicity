package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;

public record StyleDefinition<T extends Directive>(Class<T> directiveClass, T defaultValue, boolean inherited, InvalidationLevel invalidationLevel) {
	
}
