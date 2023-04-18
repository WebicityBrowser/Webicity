package com.github.webicitybrowser.thready.gui.tree.basics.imp;

import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public abstract class BaseComponent implements Component {

	private final DirectivePool directivePool = new BasicDirectivePool();
	
	@Override
	public Component directive(Directive directive) {
		directivePool.directive(directive);
		
		return this;
	}
	
	@Override
	public DirectivePool getStyleDirectives() {
		return this.directivePool;
	}

}
