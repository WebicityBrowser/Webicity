package com.github.webicitybrowser.webicity.renderer.backend.html;


import com.github.webicitybrowser.spec.css.parser.CSSParser;
import com.github.webicitybrowser.spec.css.parser.tokenizer.CSSTokenizer;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;

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

}
