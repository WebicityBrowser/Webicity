package com.github.webicitybrowser.threadyweb.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.OuterDisplay;

public interface OuterDisplayDirective extends Directive {
	
	OuterDisplay getOuterDisplay();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return OuterDisplayDirective.class;
	}

	static OuterDisplayDirective of(OuterDisplay display) {
		return () -> display;
	}
	
}
