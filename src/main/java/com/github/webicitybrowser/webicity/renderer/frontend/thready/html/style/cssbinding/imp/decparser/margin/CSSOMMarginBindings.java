package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.margin;

import java.util.Map;

import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.BottomMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.LeftMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.RightMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.TopMarginDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public final class CSSOMMarginBindings {
	
	private CSSOMMarginBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("margin-left",
			new CSSOMLonghandMarginDeclarationParser<>(MarginDirective::ofLeft, LeftMarginDirective.class));
		namedDeclarationParsers.put("margin-right",
			new CSSOMLonghandMarginDeclarationParser<>(MarginDirective::ofRight, RightMarginDirective.class));
		namedDeclarationParsers.put("margin-top",
			new CSSOMLonghandMarginDeclarationParser<>(MarginDirective::ofTop, TopMarginDirective.class));
		namedDeclarationParsers.put("margin-bottom",
			new CSSOMLonghandMarginDeclarationParser<>(MarginDirective::ofBottom, BottomMarginDirective.class));
		namedDeclarationParsers.put("margin", new CSSOMShorthandMarginDeclarationParser());
	}

}
