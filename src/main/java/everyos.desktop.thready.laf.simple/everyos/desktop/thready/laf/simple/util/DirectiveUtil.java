package everyos.desktop.thready.laf.simple.util;

import everyos.desktop.thready.basic.directive.BackgroundColorDirective;
import everyos.desktop.thready.basic.directive.FontDirective;
import everyos.desktop.thready.basic.directive.ForegroundColorDirective;
import everyos.desktop.thready.core.graphics.color.Colors;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.graphics.text.FontInfo;
import everyos.desktop.thready.core.gui.directive.DirectivePool;

public final class DirectiveUtil {

	private DirectiveUtil() {}
	
	public static FontInfo getFont(DirectivePool directives) {
		return directives
			.inheritDirectiveOrEmpty(FontDirective.class)
			.map(directive -> directive.getFont())
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
