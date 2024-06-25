package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.stylesheet.CSSStyleSheet;
import com.github.webicitybrowser.spec.css.stylesheet.StyleSheetList;

public class DocumentStyleSheetSet {

	private final List<CSSRuleList> uaRules = new ArrayList<>();
	private final StyleSheetList styleSheets;
	
	public DocumentStyleSheetSet(StyleSheetList styleSheets) {
		this.styleSheets = styleSheets;
	}
	
	public CSSRuleList[] getRuleLists() {
		List<CSSRuleList> ruleLists = new ArrayList<>();
		for (int i = 0; i < styleSheets.getLength(); i++) {
			CSSStyleSheet styleSheet = styleSheets.getItem(i);
			ruleLists.add(styleSheet.getCSSRules());
		}
		ruleLists.addAll(uaRules);
		
		return ruleLists.toArray(new CSSRuleList[0]);
	};
	
	public void addUARules(CSSRuleList rules) {
		uaRules.add(rules);
	}
	
}
