package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import com.github.webicitybrowser.spec.css.property.fontweight.FontWeightValue;
import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.drawing.core.text.CommonFontWeights;
import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.ForegroundColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.FontWeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective.OuterDisplay;

public final class WebDirectiveUtil {

	private WebDirectiveUtil() {}
	
	public static FontSource getFontSource(DirectivePool directives) {
		return WebDefaults.FONT.fontSource();
	}

	public static int getFontSize(DirectivePool directives) {
		return WebDefaults.FONT.fontSize();
	}

	public static FontWeightValue getFontWeight(DirectivePool directives) {
		return directives
			.inheritDirectiveOrEmpty(FontWeightDirective.class)
			.map(directive -> directive.getFontWeight())
			.orElse(_1 -> CommonFontWeights.NORMAL);
	}

	public static ColorFormat getForegroundColor(DirectivePool directives) {
		return directives
			.inheritDirectiveOrEmpty(ForegroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.BLACK);
	}
	
	public static ColorFormat getBackgroundColor(DirectivePool directives) {
		return directives
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}
	
	public static OuterDisplay getOuterDisplay(DirectivePool directives) {
		return directives
			.getDirectiveOrEmpty(OuterDisplayDirective.class)
			.map(directive -> directive.getOuterDisplay())
			.orElse(OuterDisplay.INLINE);
	}
	
}
