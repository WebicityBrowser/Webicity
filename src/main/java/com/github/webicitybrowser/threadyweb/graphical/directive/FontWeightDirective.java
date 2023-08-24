package com.github.webicitybrowser.threadyweb.graphical.directive;

import com.github.webicitybrowser.spec.css.property.fontweight.FontWeightValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface FontWeightDirective extends Directive {
	
	FontWeightValue getFontWeight();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FontWeightDirective.class;
	}

	static FontWeightDirective of(FontWeightValue fontWeight) {
		return () -> fontWeight;
	}

}
