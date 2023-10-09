package com.github.webicitybrowser.webicity.renderer.backend.html.tags;

import com.github.webicitybrowser.spec.css.parser.CSSParser;
import com.github.webicitybrowser.spec.css.parser.tokenizer.CSSTokenizer;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.dom.node.Element;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

public interface TagAction {

	CSSRuleList getCSSRuleList(Element element);

	default CSSRuleList createRuleList(Reader childContextReader) {
		try {
			Token[] tokens = CSSTokenizer.create().tokenize(childContextReader);
			CSSRule[] rules = CSSParser.create().parseAListOfRules(tokens);
			return CSSRuleList.create(rules);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	static TagAction getAction(String elementName) {
		HashMap<String, TagAction> actions = new HashMap<>();
		actions.put("link", new LinkTagHandler());
		actions.put("style", new StyleTagHandler());

		return actions.getOrDefault(elementName, new NoTagAction());
	}

}
