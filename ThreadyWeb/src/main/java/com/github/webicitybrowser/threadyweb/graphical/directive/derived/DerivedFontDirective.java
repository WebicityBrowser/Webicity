package com.github.webicitybrowser.threadyweb.graphical.directive.derived;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface DerivedFontDirective extends Directive {
	
	Font2D getFont();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return DerivedFontDirective.class;
	}

	static DerivedFontDirective of(Font2D value) {
		return () -> value;
	}

}
