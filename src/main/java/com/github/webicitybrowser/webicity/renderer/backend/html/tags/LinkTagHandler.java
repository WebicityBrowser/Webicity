package com.github.webicitybrowser.webicity.renderer.backend.html.tags;

import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.rule.imp.CSSRuleListImp;
import com.github.webicitybrowser.spec.dom.node.Element;

import java.io.InputStreamReader;

public class LinkTagHandler implements TagAction{

	@Override
	public CSSRuleList getCSSRuleList(Element element) {
		if(!element.hasAttribute("href") && !element.hasAttribute("imagesrcset")) {
			return new CSSRuleListImp(new CSSRule[] {});
		}
		return createRulesFromExternalResources(element.getAttribute("href"));
	}

	private CSSRuleList createRulesFromExternalResources(String href) {
		return createRuleList(new InputStreamReader(
			ClassLoader.getSystemClassLoader().getResourceAsStream("stylesheets/" + href)
		));
	}

}
