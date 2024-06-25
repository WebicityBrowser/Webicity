package com.github.webicitybrowser.thready.gui.directive.core.pool;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface DirectivePoolListener {

	void onDirective(Directive directive);
	
	void onMassChange();
	
}
