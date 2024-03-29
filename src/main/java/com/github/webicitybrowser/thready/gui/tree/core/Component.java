package com.github.webicitybrowser.thready.gui.tree.core;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;

public interface Component extends UINode {

	Class<? extends Component> getPrimaryType();

	Component directive(Directive directive);

	DirectivePool getStyleDirectives();
	
}
