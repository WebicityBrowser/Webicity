package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.border;

import java.util.Map;

import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective.BottomBorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective.LeftBorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective.RightBorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective.TopBorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.BottomBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.LeftBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.RightBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.TopBorderWidthDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public final class CSSOMBorderBindings {
	
	private CSSOMBorderBindings() {}

	public static void installTo(Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers) {
		namedDeclarationParsers.put("border-left-width",
			new CSSOMLonghandBorderWidthParser<>(BorderWidthDirective::ofLeft, LeftBorderWidthDirective.class));
		namedDeclarationParsers.put("border-right-width",
			new CSSOMLonghandBorderWidthParser<>(BorderWidthDirective::ofRight, RightBorderWidthDirective.class));
		namedDeclarationParsers.put("border-top-width",
			new CSSOMLonghandBorderWidthParser<>(BorderWidthDirective::ofTop, TopBorderWidthDirective.class));
		namedDeclarationParsers.put("border-bottom-width",
			new CSSOMLonghandBorderWidthParser<>(BorderWidthDirective::ofBottom, BottomBorderWidthDirective.class));
		namedDeclarationParsers.put("border-width", new CSSOMShorthandBorderWidthParser());

		namedDeclarationParsers.put("border-left-color",
			new CSSOMLonghandBorderColorParser<>(BorderColorDirective::ofLeft, LeftBorderColorDirective.class));
		namedDeclarationParsers.put("border-right-color",
			new CSSOMLonghandBorderColorParser<>(BorderColorDirective::ofRight, RightBorderColorDirective.class));
		namedDeclarationParsers.put("border-top-color",
			new CSSOMLonghandBorderColorParser<>(BorderColorDirective::ofTop, TopBorderColorDirective.class));
		namedDeclarationParsers.put("border-bottom-color",
			new CSSOMLonghandBorderColorParser<>(BorderColorDirective::ofBottom, BottomBorderColorDirective.class));
		namedDeclarationParsers.put("border-color", new CSSOMShorthandBorderColorParser());

		namedDeclarationParsers.put("border-left", new CSSOMComponentCompositeParser<>(
			BorderColorDirective::ofLeft, LeftBorderColorDirective.class,
			BorderWidthDirective::ofLeft, LeftBorderWidthDirective.class));
		namedDeclarationParsers.put("border-right", new CSSOMComponentCompositeParser<>(
			BorderColorDirective::ofRight, RightBorderColorDirective.class,
			BorderWidthDirective::ofRight, RightBorderWidthDirective.class));
		namedDeclarationParsers.put("border-top", new CSSOMComponentCompositeParser<>(
			BorderColorDirective::ofTop, TopBorderColorDirective.class,
			BorderWidthDirective::ofTop, TopBorderWidthDirective.class));
		namedDeclarationParsers.put("border-bottom", new CSSOMComponentCompositeParser<>(
			BorderColorDirective::ofBottom, BottomBorderColorDirective.class,
			BorderWidthDirective::ofBottom, BottomBorderWidthDirective.class));
		namedDeclarationParsers.put("border", new CSSOMWholeCompositeParser());
	}

}
