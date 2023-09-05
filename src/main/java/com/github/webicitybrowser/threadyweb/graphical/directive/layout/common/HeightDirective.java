package com.github.webicitybrowser.threadyweb.graphical.directive.layout.common;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface HeightDirective extends Directive {
	
	SizeCalculation getHeightCalculation();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return HeightDirective.class;
	}

	static HeightDirective of(SizeCalculation heightCalculation) {
		return () -> heightCalculation;
	}

}
