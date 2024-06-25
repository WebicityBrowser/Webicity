package com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface FlexJustifyContentDirective extends Directive {
	
	FlexJustifyContent getJustifyContent();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FlexJustifyContentDirective.class;
	}

	static FlexJustifyContentDirective of(FlexJustifyContent justifyContent) {
		return () -> justifyContent;
	}

	enum FlexJustifyContent {
		FLEX_START, FLEX_END, CENTER, SPACE_BETWEEN, SPACE_AROUND
	}

}
