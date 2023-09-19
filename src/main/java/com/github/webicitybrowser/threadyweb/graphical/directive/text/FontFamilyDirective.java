package com.github.webicitybrowser.threadyweb.graphical.directive.text;

import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface FontFamilyDirective extends Directive {
	
	FontSource[] getFontFamilies();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FontFamilyDirective.class;
	}

	static FontFamilyDirective of(FontSource... fontFamilies) {
		return () -> fontFamilies;
	}

}
