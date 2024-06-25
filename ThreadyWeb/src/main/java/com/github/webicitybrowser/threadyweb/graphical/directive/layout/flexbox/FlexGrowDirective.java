package com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface FlexGrowDirective extends Directive {
	
	float getFlexGrow();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FlexGrowDirective.class;
	}
	
	static FlexGrowDirective of(float flexGrow) {
		return () -> flexGrow;
	}

}
