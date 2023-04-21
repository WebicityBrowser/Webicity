package com.github.webicitybrowser.spec.css.parser;

import com.github.webicitybrowser.spec.css.parser.imp.CSSParserImp;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.rule.CSSRule;

public interface CSSParser {

	// TODO: Other entry points
	
	CSSRule[] parseAListOfRules(Token[] tokens);
	
	CSSRule[] parseAListOfDeclarations(TokenLike[] tokens);
	
	public static CSSParser create() {
		return new CSSParserImp();
	}
	
}
