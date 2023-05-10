package com.github.webicitybrowser.webicity.renderer.backend.html;



import java.io.IOException;
import java.io.StringReader;

import com.github.webicitybrowser.spec.css.parser.CSSParser;
import com.github.webicitybrowser.spec.css.parser.tokenizer.CSSTokenizer;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.stylesheet.CSSStyleSheet;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.imp.util.DOMTextUtil;
import com.github.webicitybrowser.spec.html.node.HTMLDocument;
import com.github.webicitybrowser.spec.html.parse.ParserSettings;
import com.github.webicitybrowser.spec.infra.Namespace;

public class HTMLRendererBackendParserSettings implements ParserSettings {

	@Override
	public void onNodePopped(Node node) {
		if (
			node instanceof Element element &&
			element.getLocalName().equals("style") &&
			element.getNamespace().equals(Namespace.HTML_NAMESPACE)
		) {
			addDocumentStylesheet(element);
		}
	}

	private void addDocumentStylesheet(Element element) {
		CSSRuleList ruleList = createRuleList(DOMTextUtil.getChildTextContent(element));
		CSSStyleSheet styleSheet = () -> ruleList;
		((HTMLDocument) element.getOwnerDocument()).getStyleSheets().add(styleSheet);
	}

	private CSSRuleList createRuleList(String childTextContent) {
		StringReader reader = new StringReader(childTextContent);
		try {
			Token[] tokens = CSSTokenizer.create().tokenize(reader);
			CSSRule[] rules = CSSParser.create().parseAListOfRules(tokens);
			return CSSRuleList.create(rules);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
