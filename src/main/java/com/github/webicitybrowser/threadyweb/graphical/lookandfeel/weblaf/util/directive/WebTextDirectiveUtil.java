package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive;

import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.FontSizeDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.FontWeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.WhiteSpaceCollapseDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDefaults;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;
import com.github.webicitybrowser.threadyweb.graphical.value.WhiteSpaceCollapse;

public final class WebTextDirectiveUtil {
	
	private WebTextDirectiveUtil() {}

	public static FontSource getFontSource(DirectivePool directives) {
		return WebDefaults.FONT.fontSource();
	}

	public static float getFontSize(DirectivePool directives, SizeCalculationContext context) {
		return directives
			.getDirectiveOrEmpty(FontSizeDirective.class)
			.map(directive -> directive.getSizeCalculation())
			.map(calculation -> calculation.calculate(context))
			.orElse(context.relativeFont().getSize());
	}

	public static int getFontWeight(DirectivePool directives, FontMetrics parentMetrics) {
		return directives
			.getDirectiveOrEmpty(FontWeightDirective.class)
			.map(directive -> directive.getFontWeight())
			.map(weight -> weight.getWeight(parentMetrics.getWeight()))
			.orElse(parentMetrics.getWeight());
	}

	public static WhiteSpaceCollapse getWhiteSpaceCollapse(DirectivePool directives) {
		return directives
			.getDirectiveOrEmpty(WhiteSpaceCollapseDirective.class)
			.map(directive -> directive.getWhiteSpaceCollapse())
			.orElse(WhiteSpaceCollapse.COLLAPSE);
	}


}
