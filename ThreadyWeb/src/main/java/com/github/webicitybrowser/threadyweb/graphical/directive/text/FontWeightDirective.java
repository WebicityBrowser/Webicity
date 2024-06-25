package com.github.webicitybrowser.threadyweb.graphical.directive.text;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface FontWeightDirective extends Directive {
	
	FontWeight getFontWeight();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FontWeightDirective.class;
	}

	static FontWeightDirective of(FontWeight fontWeight) {
		return () -> fontWeight;
	}

	interface FontWeight {
		int getWeight(int parentWeight);
	}

}
