package com.github.webicitybrowser.thready.gui.tree.core;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;

public interface Component extends UINode {

	Class<? extends Component> getPrimaryType();

	void directive(Directive directive);

	DirectivePool getStyleDirectives();
	
}
