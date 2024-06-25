package com.github.webicitybrowser.webicity.renderer.backend.html;


import com.github.webicitybrowser.spec.css.parser.CSSParser;
import com.github.webicitybrowser.spec.css.parser.tokenizer.CSSTokenizer;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.stylesheet.CSSStyleSheet;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.html.node.HTMLDocument;

import java.io.IOException;
import java.io.Reader;

public final class CSSRulesUtils {

	private CSSRulesUtils() {}

	public static CSSRuleList createRuleList(Reader childContextReader) {
		try {
			Token[] tokens = CSSTokenizer.create().tokenize(childContextReader);
			CSSRule[] rules = CSSParser.create().parseAListOfRules(tokens);
			return CSSRuleList.create(rules);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static CSSRuleList createDeclarationRuleList(Reader childContextReader) {
		try {
			Token[] tokens = CSSTokenizer.create().tokenize(childContextReader);
			CSSRule[] rules = CSSParser.create().parseAListOfDeclarations(tokens);
			return CSSRuleList.create(rules);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void addStylesheet(CSSRuleList cssRuleList, Element element) {
		CSSRuleList ruleList = cssRuleList;
		if(ruleList.getLength() > 0) {
			CSSStyleSheet styleSheet = () -> ruleList;
			((HTMLDocument) element.getOwnerDocument()).getStyleSheets().add(styleSheet);
		}
	}

}
