package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.text;

import java.util.Map;

import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public final class CSSOMTextBindings {
	
	private CSSOMTextBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("font", new CSSOMFontShorthandDeclarationParser());
		namedDeclarationParsers.put("font-family", new CSSOMFontFamilyDeclarationParser());
		namedDeclarationParsers.put("font-weight", new CSSOMFontWeightDeclarationParser());
		namedDeclarationParsers.put("font-size", new CSSOMFontSizeDeclarationParser());
		namedDeclarationParsers.put("letter-spacing", new CSSOMLetterSpacingDeclarationParser());
		namedDeclarationParsers.put("line-break", new CSSOMLineBreakDeclarationParser());
		namedDeclarationParsers.put("text-align", new CSSOMTextAlignDeclarationParser());
	}

}
