package com.github.webicitybrowser.threadyweb.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.ClearDirection;

public interface ClearDirective extends Directive {
	
	ClearDirection getClearDirection();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return ClearDirective.class;
	}

	static ClearDirective of(ClearDirection clearDirection) {
		return () -> clearDirection;
	}

}
