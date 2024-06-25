package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface CSSOMDeclarationParser {

	Directive[] parseDeclaration(String name, TokenLike[] tokens);

	CSSOMNamedDeclarationParser<?> getNamedDeclarationParser(String name);

	List<String> getDirectivePropertyNames(Class<? extends Directive> directiveClass);

	static CSSOMDeclarationParser create() {
		return new CSSOMDeclarationParserImp();
	}

}
