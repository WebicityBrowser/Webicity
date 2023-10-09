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
import com.github.webicitybrowser.spec.css.rule.imp.CSSRuleListImp;
import com.github.webicitybrowser.spec.css.stylesheet.CSSStyleSheet;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.imp.util.DOMTextUtil;
import com.github.webicitybrowser.spec.html.node.HTMLDocument;
import com.github.webicitybrowser.spec.html.parse.ParserSettings;
import com.github.webicitybrowser.spec.html.parse.CharacterReferenceLookup;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.webicity.renderer.backend.html.tags.TagAction;


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
		if(
			node instanceof Element element &&
			element.getNamespace().equals(Namespace.HTML_NAMESPACE)
		) {
			TagAction tagAction = TagAction.getAction(element.getLocalName());
			CSSRuleList ruleList = tagAction.getCSSRuleList(element);
			if(ruleList.getLength() > 0) {
				CSSStyleSheet styleSheet = () -> ruleList;
				((HTMLDocument) element.getOwnerDocument()).getStyleSheets().add(styleSheet);
			}
		}
	}

}
