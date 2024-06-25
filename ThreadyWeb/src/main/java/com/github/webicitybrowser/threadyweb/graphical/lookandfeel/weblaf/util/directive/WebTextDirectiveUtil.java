package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.WhiteSpaceCollapseDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LetterSpacingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LineBreakDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LineBreakDirective.LineBreak;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;
import com.github.webicitybrowser.threadyweb.graphical.value.WhiteSpaceCollapse;

public final class WebTextDirectiveUtil {
	
	private WebTextDirectiveUtil() {}

	public static WhiteSpaceCollapse getWhiteSpaceCollapse(DirectivePool directives) {
		return directives
			.getDirectiveOrEmpty(WhiteSpaceCollapseDirective.class)
			.map(directive -> directive.getWhiteSpaceCollapse())
			.orElse(WhiteSpaceCollapse.COLLAPSE);
	}

	public static float getLetterSpacing(DirectivePool directives, SizeCalculationContext context) {
		return directives
			.inheritDirectiveOrEmpty(LetterSpacingDirective.class)
			.map(directive -> directive.getLetterSpacing())
			.map(letterSpacing -> letterSpacing.calculate(context))
			.orElse(0f);
	}

	public static LineBreak getLineBreak(DirectivePool styleDirectives) {
		return styleDirectives
			.getDirectiveOrEmpty(LineBreakDirective.class)
			.map(directive -> directive.getLineBreak())
			.orElse(LineBreak.NORMAL);
	}

}
