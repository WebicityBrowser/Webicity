package com.github.webicitybrowser.threadyweb.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface OuterDisplayDirective extends Directive {
	
	OuterDisplay getOuterDisplay();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return OuterDisplayDirective.class;
	}
	
	public static enum OuterDisplay {
		BLOCK, INLINE,
		NONE, CONTENTS
	}

	public static OuterDisplayDirective of(OuterDisplay display) {
		return () -> display;
	}
	
}
