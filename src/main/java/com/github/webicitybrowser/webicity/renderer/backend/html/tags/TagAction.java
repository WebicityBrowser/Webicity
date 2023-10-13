package com.github.webicitybrowser.webicity.renderer.backend.html.tags;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.stylesheet.CSSStyleSheet;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.html.node.HTMLDocument;


public interface TagAction {

	void handleTag(Element element);

	default void onTagParsed(Element element) {
		handleTags(element);
	}

	private void handleTags(Element element) {
		handleTag(element);
	}

}
