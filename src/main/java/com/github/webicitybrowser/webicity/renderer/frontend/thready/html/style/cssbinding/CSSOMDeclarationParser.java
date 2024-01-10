package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMDeclarationParserImp;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public interface CSSOMDeclarationParser {

	Directive[] parseDeclaration(String name, TokenLike[] tokens);

	CSSOMNamedDeclarationParser<?> getNamedDeclarationParser(String name);

	static CSSOMDeclarationParser create() {
		return new CSSOMDeclarationParserImp();
	}

}
