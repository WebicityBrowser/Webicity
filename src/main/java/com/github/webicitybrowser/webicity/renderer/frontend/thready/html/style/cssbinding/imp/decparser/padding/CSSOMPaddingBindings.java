package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.padding;

import java.util.Map;

import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.margin.CSSOMLonghandMarginDeclarationParser;

public final class CSSOMPaddingBindings {
	
	private CSSOMPaddingBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("padding-left", new CSSOMLonghandMarginDeclarationParser(PaddingDirective::ofLeft));
		namedDeclarationParsers.put("padding-right", new CSSOMLonghandMarginDeclarationParser(PaddingDirective::ofRight));
		namedDeclarationParsers.put("padding-top", new CSSOMLonghandMarginDeclarationParser(PaddingDirective::ofTop));
		namedDeclarationParsers.put("padding-bottom", new CSSOMLonghandMarginDeclarationParser(PaddingDirective::ofBottom));
		namedDeclarationParsers.put("padding", new CSSOMShorthandPaddingParser());
	}

}
