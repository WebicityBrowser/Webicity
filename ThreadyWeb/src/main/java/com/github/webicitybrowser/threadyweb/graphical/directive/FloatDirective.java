package com.github.webicitybrowser.threadyweb.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.FloatDirection;

public interface FloatDirective extends Directive {
	
	FloatDirection getFloatDirection();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FloatDirective.class;
	}

	static FloatDirective of(FloatDirection floatDirection) {
		return () -> floatDirection;
	}

}
