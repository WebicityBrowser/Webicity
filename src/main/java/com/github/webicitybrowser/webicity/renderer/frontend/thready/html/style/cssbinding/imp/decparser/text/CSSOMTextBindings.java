package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.text;

import java.util.Map;

import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMTextBindings {
	
	private CSSOMTextBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("font-family", new CSSOMFontFamilyParser());
		namedDeclarationParsers.put("font-weight", new CSSOMFontWeightDeclarationParser());
		namedDeclarationParsers.put("font-size", new CSSOMFontSizeDeclarationParser());
		namedDeclarationParsers.put("letter-spacing", new CSSOMLetterSpacingDeclarationParser());
	}

}
