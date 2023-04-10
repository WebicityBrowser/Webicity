package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.directive.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.directive.FontDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.directive.ForegroundColorDirective;

public final class SimpleDirectiveUtil {

private SimpleDirectiveUtil() {}
	
	public static FontSettings getFontSettings(DirectivePool directives) {
		return directives
			.inheritDirectiveOrEmpty(FontDirective.class)
			.map(directive -> directive.getFontSettings())
			.orElse(SimpleDefaults.FONT);
	}

	public static ColorFormat getForegroundColor(DirectivePool directives) {
		return directives
			.inheritDirectiveOrEmpty(ForegroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.WHITE);
	}
	
	public static ColorFormat getBackgroundColor(DirectivePool directives) {
		return directives
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}
	
}
