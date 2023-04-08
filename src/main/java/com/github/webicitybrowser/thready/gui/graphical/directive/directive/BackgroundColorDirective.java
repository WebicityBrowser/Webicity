package com.github.webicitybrowser.thready.gui.graphical.directive.directive;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface BackgroundColorDirective extends Directive {
	
	ColorFormat getColor();
	
	default Class<? extends Directive> getPrimaryType() {
		return BackgroundColorDirective.class;
	}

	public static BackgroundColorDirective of(ColorFormat color) {
		return () -> color;
	}

}
