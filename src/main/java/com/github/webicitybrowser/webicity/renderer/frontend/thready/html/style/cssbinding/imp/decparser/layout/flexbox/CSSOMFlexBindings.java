package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.layout.flexbox;

import java.util.Map;

import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexGrowDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMFlexBindings {

	private CSSOMFlexBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("flex", new CSSOMFlexDeclarationParser());
		namedDeclarationParsers.put("flex-direction", new CSSOMFlexDirectionDeclarationParser());
		namedDeclarationParsers.put("flex-wrap", new CSSOMFlexWrapDeclarationParser());
		namedDeclarationParsers.put("flex-grow", new CSSOMFlexFactorDeclarationParser(FlexGrowDirective::of));
		namedDeclarationParsers.put("flex-shrink", new CSSOMFlexFactorDeclarationParser(FlexGrowDirective::of));
		namedDeclarationParsers.put("justify-content", new CSSOMFlexJustifyContentDeclarationParser());
	}

}
