package com.github.webicitybrowser.threadyweb.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.InnerDisplay;

public interface InnerDisplayDirective extends Directive {

	InnerDisplay getInnerDisplay();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return InnerDisplayDirective.class;
	}

	static InnerDisplayDirective of(InnerDisplay display) {
		return () -> display;
	}

}
