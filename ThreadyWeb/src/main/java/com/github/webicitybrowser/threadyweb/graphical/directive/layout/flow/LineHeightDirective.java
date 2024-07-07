package com.github.webicitybrowser.threadyweb.graphical.directive.layout.flow;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface LineHeightDirective extends Directive {

	static final SizeCalculation NORMAL = SizeCalculation.SIZE_AUTO;
	
	SizeCalculation getLineHeightCalculation();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return LineHeightDirective.class;
	}

	static LineHeightDirective of(SizeCalculation lineHeightCalculation) {
		return () -> lineHeightCalculation;
	}

}
