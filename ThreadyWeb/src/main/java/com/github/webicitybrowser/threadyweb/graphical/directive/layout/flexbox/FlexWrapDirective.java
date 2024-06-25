package com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface FlexWrapDirective extends Directive {
	
	FlexWrap getFlexWrap();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FlexWrapDirective.class;
	}

	static FlexWrapDirective of(FlexWrap flexWrap) {
		return () -> flexWrap;
	}

	enum FlexWrap {
		NOWRAP, WRAP, WRAP_REVERSE
	}

}
