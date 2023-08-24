package com.github.webicitybrowser.threadyweb.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface WhiteSpaceCollapseDirective extends Directive {

	WhiteSpaceCollapse getWhiteSpaceCollapse();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return WhiteSpaceCollapseDirective.class;
	}

	static WhiteSpaceCollapseDirective of(WhiteSpaceCollapse whiteSpaceCollapse) {
		return () -> whiteSpaceCollapse;
	}

	enum WhiteSpaceCollapse {
		COLLAPSE, DISCARD, PRESERVE, PRESERVE_BREAKS, PRESERVE_SPACES, BREAK_SPACES
	}
	
}
