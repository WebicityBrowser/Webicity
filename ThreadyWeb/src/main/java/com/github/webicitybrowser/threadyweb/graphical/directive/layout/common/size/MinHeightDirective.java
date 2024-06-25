package com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface MinHeightDirective extends SizeCalculationDirective {

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return MinHeightDirective.class;
	}

	static MinHeightDirective of(SizeCalculation heightCalculation) {
		return () -> heightCalculation;
	}

}
