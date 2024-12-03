package com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.SizeCalculationDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface FlexBasisDirective extends SizeCalculationDirective {
	
	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FlexBasisDirective.class;
	}

	static FlexBasisDirective of(SizeCalculation basis) {
		return () -> basis;
	}

}
