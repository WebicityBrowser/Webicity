package com.github.webicitybrowser.threadyweb.graphical.directive.layout.common;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface MaxWidthDirective extends Directive {
	
	SizeCalculation getMaxWidthCalculation();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return MaxWidthDirective.class;
	}

	static MaxWidthDirective of(SizeCalculation widthCalculation) {
		return () -> widthCalculation;
	}

}
