package com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface WidthDirective extends SizeCalculationDirective {
	
	@Override
	default Class<? extends Directive> getPrimaryType() {
		return WidthDirective.class;
	}

	static WidthDirective of(SizeCalculation widthCalculation) {
		return () -> widthCalculation;
	}

}
