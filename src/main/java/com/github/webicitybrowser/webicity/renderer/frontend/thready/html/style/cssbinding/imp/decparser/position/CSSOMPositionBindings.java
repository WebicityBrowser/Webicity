package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.position;

import java.util.Map;

import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public final class CSSOMPositionBindings {
	
	private CSSOMPositionBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("position", new CSSOMPositionTypeDeclarationParser());
		namedDeclarationParsers.put("left", new CSSOMPositionOffsetDeclarationParser(PositionOffsetDirective::ofLeft));
		namedDeclarationParsers.put("right", new CSSOMPositionOffsetDeclarationParser(PositionOffsetDirective::ofRight));
		namedDeclarationParsers.put("top", new CSSOMPositionOffsetDeclarationParser(PositionOffsetDirective::ofTop));
		namedDeclarationParsers.put("bottom", new CSSOMPositionOffsetDeclarationParser(PositionOffsetDirective::ofBottom));
	}

}
