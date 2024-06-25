package com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface FlexShrinkDirective extends Directive {
	
	float getFlexShrink();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FlexShrinkDirective.class;
	}
	
	static FlexShrinkDirective of(float flexShrink) {
		return () -> flexShrink;
	}

}
