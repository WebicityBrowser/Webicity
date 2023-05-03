package com.github.webicitybrowser.thready.gui.directive.core.pool;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface DirectivePoolListener {

	void onDirective(Class<? extends Directive> directiveCls);
	
	void onMassChange();
	
}
