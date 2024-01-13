package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.position;

import java.util.Map;

import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective.BottomPositionOffsetDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective.LeftPositionOffsetDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective.RightPositionOffsetDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective.TopPositionOffsetDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public final class CSSOMPositionBindings {
	
	private CSSOMPositionBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("position", new CSSOMPositionTypeDeclarationParser());
		namedDeclarationParsers.put("left",
			new CSSOMPositionOffsetDeclarationParser<>(PositionOffsetDirective::ofLeft, LeftPositionOffsetDirective.class));
		namedDeclarationParsers.put("right",
			new CSSOMPositionOffsetDeclarationParser<>(PositionOffsetDirective::ofRight, RightPositionOffsetDirective.class));
		namedDeclarationParsers.put("top",
			new CSSOMPositionOffsetDeclarationParser<>(PositionOffsetDirective::ofTop, TopPositionOffsetDirective.class));
		namedDeclarationParsers.put("bottom",
			new CSSOMPositionOffsetDeclarationParser<>(PositionOffsetDirective::ofBottom, BottomPositionOffsetDirective.class));
	}

}
