package com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface HeightDirective extends SizeCalculationDirective {
	
	@Override
	default Class<? extends Directive> getPrimaryType() {
		return HeightDirective.class;
	}

	static HeightDirective of(SizeCalculation heightCalculation) {
		return () -> heightCalculation;
	}

}
