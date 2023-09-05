package com.github.webicitybrowser.threadyweb.graphical.directive.layout.common;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface MinWidthDirective extends Directive {
	
	SizeCalculation getMinWidthCalculation();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return MinWidthDirective.class;
	}

	static MinWidthDirective of(SizeCalculation widthCalculation) {
		return () -> widthCalculation;
	}

}
