package com.github.webicitybrowser.webicity.renderer.backend.html.tags;

import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.rule.imp.CSSRuleListImp;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;
import com.github.webicitybrowser.webicity.renderer.backend.html.CSSRulesUtils;

import java.io.InputStreamReader;

public class LinkTagHandler implements TagAction{

	private final RendererContext context;

	public LinkTagHandler(RendererContext context) {
		this.context = context;
	}

	@Override
	public void handleTag(Element element) {
		if(!element.hasAttribute("href") && !element.hasAttribute("imagesrcset")) {
			return;
		}
		CSSRulesUtils.addStylesheet(createRulesFromExternalResources(element.getAttribute("href")), element);
	}

	private CSSRuleList createRulesFromExternalResources(String href) {
		return CSSRulesUtils.createRuleList(new InputStreamReader(
			ClassLoader.getSystemClassLoader().getResourceAsStream("stylesheets/" + href)
		));
	}

}
