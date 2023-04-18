package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface FontDirective extends Directive {

	FontSettings getFontSettings();
	
	default Class<? extends Directive> getPrimaryType() {
		return FontDirective.class;
	}

	public static FontDirective of(FontSettings fontSettings) {
		return () -> fontSettings;
	}
	
}
