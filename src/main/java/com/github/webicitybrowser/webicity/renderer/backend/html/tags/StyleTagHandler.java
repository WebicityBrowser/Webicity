package com.github.webicitybrowser.webicity.renderer.backend.html.tags;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.imp.util.DOMTextUtil;
import com.github.webicitybrowser.webicity.renderer.backend.html.CSSRulesUtils;

import java.io.StringReader;

public class StyleTagHandler implements TagAction{

	@Override
	public CSSRuleList getCSSRuleList(Element element) {
		return createRulesFromStyleTag(element);
	}

	private CSSRuleList createRulesFromStyleTag(Element element) {
		return CSSRulesUtils.createRuleList(new StringReader(DOMTextUtil.getChildTextContent(element)));
	}

}
