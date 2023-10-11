package com.github.webicitybrowser.webicity.renderer.backend.html.tags;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.stylesheet.CSSStyleSheet;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.html.node.HTMLDocument;


public interface TagAction {

	CSSRuleList getCSSRuleList(Element element);

	default void onTagParsed(Element element) {
		CSSRuleList ruleList = getCSSRuleList(element);
		if(ruleList.getLength() > 0) {
			CSSStyleSheet styleSheet = () -> ruleList;
			((HTMLDocument) element.getOwnerDocument()).getStyleSheets().add(styleSheet);
		}
	}

}
