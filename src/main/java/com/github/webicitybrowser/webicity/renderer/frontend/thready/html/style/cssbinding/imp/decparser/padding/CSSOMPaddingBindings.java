package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.padding;

import java.util.Map;

import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.margin.CSSOMLonghandMarginParser;

public final class CSSOMPaddingBindings {
	
	private CSSOMPaddingBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("padding-left", new CSSOMLonghandMarginParser(PaddingDirective::ofLeft));
		namedDeclarationParsers.put("padding-right", new CSSOMLonghandMarginParser(PaddingDirective::ofRight));
		namedDeclarationParsers.put("padding-top", new CSSOMLonghandMarginParser(PaddingDirective::ofTop));
		namedDeclarationParsers.put("padding-bottom", new CSSOMLonghandMarginParser(PaddingDirective::ofBottom));
		namedDeclarationParsers.put("padding", new CSSOMShorthandPaddingParser());
	}

}
