package com.github.webicitybrowser.threadyweb.graphical.directive.layout.common;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface MaxHeightDirective extends Directive {
	
	SizeCalculation getMaxHeightCalculation();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return MaxHeightDirective.class;
	}

	static MaxHeightDirective of(SizeCalculation heightCalculation) {
		return () -> heightCalculation;
	}

}
