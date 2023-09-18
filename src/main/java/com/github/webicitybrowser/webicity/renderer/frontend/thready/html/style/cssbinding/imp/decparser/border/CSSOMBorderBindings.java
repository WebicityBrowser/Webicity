package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.border;

import java.util.Map;

import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public final class CSSOMBorderBindings {
	
	private CSSOMBorderBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("border-left-width", new CSSOMLonghandBorderWidthParser(BorderWidthDirective::ofLeft));
		namedDeclarationParsers.put("border-right-width", new CSSOMLonghandBorderWidthParser(BorderWidthDirective::ofRight));
		namedDeclarationParsers.put("border-top-width", new CSSOMLonghandBorderWidthParser(BorderWidthDirective::ofTop));
		namedDeclarationParsers.put("border-bottom-width", new CSSOMLonghandBorderWidthParser(BorderWidthDirective::ofBottom));
		namedDeclarationParsers.put("border-width", new CSSOMShorthandBorderWidthParser());

		namedDeclarationParsers.put("border-left-color", new CSSOMLonghandBorderColorParser(BorderColorDirective::ofLeft));
		namedDeclarationParsers.put("border-right-color", new CSSOMLonghandBorderColorParser(BorderColorDirective::ofRight));
		namedDeclarationParsers.put("border-top-color", new CSSOMLonghandBorderColorParser(BorderColorDirective::ofTop));
		namedDeclarationParsers.put("border-bottom-color", new CSSOMLonghandBorderColorParser(BorderColorDirective::ofBottom));
		namedDeclarationParsers.put("border-color", new CSSOMShorthandBorderColorParser());

		namedDeclarationParsers.put("border-left", new CSSOMComponentCompositeParser(BorderColorDirective::ofLeft, BorderWidthDirective::ofLeft));
		namedDeclarationParsers.put("border-right", new CSSOMComponentCompositeParser(BorderColorDirective::ofRight, BorderWidthDirective::ofRight));
		namedDeclarationParsers.put("border-top", new CSSOMComponentCompositeParser(BorderColorDirective::ofTop, BorderWidthDirective::ofTop));
		namedDeclarationParsers.put("border-bottom", new CSSOMComponentCompositeParser(BorderColorDirective::ofBottom, BorderWidthDirective::ofBottom));
		namedDeclarationParsers.put("border", new CSSOMWholeCompositeParser());
	}

}
