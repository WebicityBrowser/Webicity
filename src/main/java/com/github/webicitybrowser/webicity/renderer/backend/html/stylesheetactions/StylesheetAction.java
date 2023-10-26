package com.github.webicitybrowser.webicity.renderer.backend.html.stylesheetactions;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.webicity.renderer.backend.html.CSSRulesUtils;

public class StylesheetAction implements LinkAction {

	@Override
	public void processThisTypeOfLinkedResource(Element el, boolean success, FetchResponse response, byte[] bodyBytes) {
		Reader childContextReader = new InputStreamReader(new ByteArrayInputStream(bodyBytes));
		CSSRuleList ruleList = CSSRulesUtils.createRuleList(childContextReader);
		CSSRulesUtils.addStylesheet(ruleList, el);
	}

}
