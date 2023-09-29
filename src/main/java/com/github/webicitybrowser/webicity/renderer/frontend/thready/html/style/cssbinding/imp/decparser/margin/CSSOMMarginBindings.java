package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.margin;

import java.util.Map;

import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public final class CSSOMMarginBindings {
	
	private CSSOMMarginBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("margin-left", new CSSOMLonghandMarginParser(MarginDirective::ofLeft));
		namedDeclarationParsers.put("margin-right", new CSSOMLonghandMarginParser(MarginDirective::ofRight));
		namedDeclarationParsers.put("margin-top", new CSSOMLonghandMarginParser(MarginDirective::ofTop));
		namedDeclarationParsers.put("margin-bottom", new CSSOMLonghandMarginParser(MarginDirective::ofBottom));
		namedDeclarationParsers.put("margin", new CSSOMShorthandMarginParser());
	}

}
