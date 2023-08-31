package com.github.webicitybrowser.threadyweb.graphical.directive;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface FontSizeDirective extends Directive {
	
	SizeCalculation getSizeCalculation();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FontSizeDirective.class;
	}

	static FontSizeDirective of(SizeCalculation sizeCalculation) {
		return () -> sizeCalculation;
	}

}
