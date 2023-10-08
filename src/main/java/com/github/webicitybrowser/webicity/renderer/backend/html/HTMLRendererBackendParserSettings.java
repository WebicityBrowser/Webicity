package com.github.webicitybrowser.webicity.renderer.backend.html;


import java.io.StringReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;

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
import com.github.webicitybrowser.spec.html.parse.CharacterReferenceLookup;
import com.github.webicitybrowser.spec.infra.Namespace;


public class HTMLRendererBackendParserSettings implements ParserSettings {
	
	private final CharacterReferenceLookup unicodeLookup;

	public HTMLRendererBackendParserSettings(CharacterReferenceLookup unicodeLookup) {
		this.unicodeLookup = unicodeLookup;
	}

	@Override
	public CharacterReferenceLookup getUnicodeLookup() {
		return this.unicodeLookup;
	}

	@Override
	public void onNodePopped(Node node) {
		if (
			node instanceof Element element &&
			element.getLocalName().equals("style") &&
			element.getNamespace().equals(Namespace.HTML_NAMESPACE)
		) {
			addStylesheet(element, createRulesFromStyleTag(element));
		} else if( node instanceof Element element &&
				element.getLocalName().equals("link") &&
				element.getNamespace().equals(Namespace.HTML_NAMESPACE)
		) {
			addStylesheet(element, createRulesFromExternalResources(element));
		}
	}

	private void addStylesheet(Element element, CSSRuleList ruleList) {
		CSSStyleSheet styleSheet = () -> ruleList;
		((HTMLDocument) element.getOwnerDocument()).getStyleSheets().add(styleSheet);
	}

	private CSSRuleList createRulesFromStyleTag(Element element) {
		return createRuleList(new StringReader(DOMTextUtil.getChildTextContent(element)));
	}

	private CSSRuleList createRulesFromExternalResources(Element element) {
		if(!element.hasAttribute("href") && !element.hasAttribute("imagesrcset")) {
			throw new RuntimeException("neither href nor imagesrcset is present in link tag");
		} else if(element.getAttribute("href").isEmpty()) {
			throw new RuntimeException("href is present but empty");
		}

		//TODO: add complete error checking

		String href = element.getAttribute("href");
		return createRuleList(new InputStreamReader(
			ClassLoader.getSystemClassLoader().getResourceAsStream("stylesheets/" + href)
		));
	}

	private CSSRuleList createRuleList(Reader childContextReader) {
		try {
			Token[] tokens = CSSTokenizer.create().tokenize(childContextReader);
			CSSRule[] rules = CSSParser.create().parseAListOfRules(tokens);
			return CSSRuleList.create(rules);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
