package everyos.desktop.thready.laf.simple.util;

import everyos.desktop.thready.basic.directive.FontDirective;
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
	
}
