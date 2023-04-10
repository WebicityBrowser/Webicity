package com.github.webicitybrowser.thready.gui.graphical.directive.directive;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface ForegroundColorDirective extends Directive {
	
	ColorFormat getColor();
	
	default Class<? extends Directive> getPrimaryType() {
		return ForegroundColorDirective.class;
	}

	public static ForegroundColorDirective of(ColorFormat color) {
		return () -> color;
	}

}
