package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.background;

import java.util.Map;

import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public final class CSSOMBackgroundBindings {
	
	private CSSOMBackgroundBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("background", new CSSOMBackgroundDeclarationParser());
		namedDeclarationParsers.put("background-color", new CSSOMBackgroundColorDeclarationParser());
	}

}
