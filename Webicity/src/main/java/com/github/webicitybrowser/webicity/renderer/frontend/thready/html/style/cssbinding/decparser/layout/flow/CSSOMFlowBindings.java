package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.layout.flow;

import java.util.Map;

import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public final class CSSOMFlowBindings {
	
	private CSSOMFlowBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("line-height", new CSSOMLineHeightDeclarationParser());
	}

}
