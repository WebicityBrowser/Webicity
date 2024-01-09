package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.padding;

import java.util.Map;

import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.BottomPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.LeftPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.RightPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.TopPaddingDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public final class CSSOMPaddingBindings {
	
	private CSSOMPaddingBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("padding-left",
			new CSSOMLonghandPaddingDeclarationParser<>(PaddingDirective::ofLeft, LeftPaddingDirective.class));
		namedDeclarationParsers.put("padding-right",
			new CSSOMLonghandPaddingDeclarationParser<>(PaddingDirective::ofRight, RightPaddingDirective.class));
		namedDeclarationParsers.put("padding-top",
			new CSSOMLonghandPaddingDeclarationParser<>(PaddingDirective::ofTop, TopPaddingDirective.class));
		namedDeclarationParsers.put("padding-bottom",
			new CSSOMLonghandPaddingDeclarationParser<>(PaddingDirective::ofBottom, BottomPaddingDirective.class));
		namedDeclarationParsers.put("padding", new CSSOMShorthandPaddingDeclarationParser());
	}

}
