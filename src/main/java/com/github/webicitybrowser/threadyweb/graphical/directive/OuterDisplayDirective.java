package com.github.webicitybrowser.threadyweb.graphical.directive;

import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.OuterDisplay;

public interface OuterDisplayDirective extends Directive {

	// This would be private if Java allowed it - do not use this map directly
	static final Map<OuterDisplay, OuterDisplayDirective> DIRECTIVE_CACHE = new HashMap<>();
	
	OuterDisplay getOuterDisplay();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return OuterDisplayDirective.class;
	}

	static OuterDisplayDirective of(OuterDisplay display) {
		return DIRECTIVE_CACHE.computeIfAbsent(display, _1 -> ((OuterDisplayDirective) () -> display));
	}
	
}
