package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;

public interface FontDirective extends GraphicalDirective {

	FontSettings getFontSettings();
	
	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FontDirective.class;
	}
	
	@Override
	default InvalidationLevel getInvalidationLevel() {
		return InvalidationLevel.RENDER;
	};

	public static FontDirective of(FontSettings fontSettings) {
		return () -> fontSettings;
	}
	
}
