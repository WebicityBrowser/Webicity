package com.github.webicitybrowser.webicity.renderer.backend.html.stylesheetactions;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.webicity.renderer.backend.html.CSSRulesUtils;

public class StylesheetAction implements LinkAction {

	@Override
	public void processThisTypeOfLinkedResource(Element el, boolean success, FetchResponse response, byte[] bodyBytes) {
		CSSRuleList ruleList = CSSRulesUtils.createRuleList(response.body().readableStream());
		CSSRulesUtils.addStylesheet(ruleList, el);
	}

}
