package com.github.webicitybrowser.webicity.renderer.backend.html.tags;

import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.rule.imp.CSSRuleListImp;
import com.github.webicitybrowser.spec.dom.node.Element;


public class NoTagAction implements TagAction{

	@Override
	public CSSRuleList getCSSRuleList(Element element) {
		return new CSSRuleListImp(new CSSRule[] {});
	}

}
