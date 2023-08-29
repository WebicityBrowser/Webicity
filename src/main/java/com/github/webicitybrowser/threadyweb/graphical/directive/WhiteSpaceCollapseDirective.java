package com.github.webicitybrowser.threadyweb.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.WhiteSpaceCollapse;

public interface WhiteSpaceCollapseDirective extends Directive {

	WhiteSpaceCollapse getWhiteSpaceCollapse();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return WhiteSpaceCollapseDirective.class;
	}

	static WhiteSpaceCollapseDirective of(WhiteSpaceCollapse whiteSpaceCollapse) {
		return () -> whiteSpaceCollapse;
	}
	
}
