package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleDefinition;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface LookAndFeel {

	ComponentUI createUIFor(Component component, ComponentUI parent);

	<T extends Directive> StyleDefinition<T> getStyleDefinition(Class<T> directiveType);
	
}
