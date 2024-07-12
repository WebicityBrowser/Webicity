package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.selectors.SelectorSpecificity.Source;
import com.github.webicitybrowser.spec.css.stylesheet.CSSStyleSheet;
import com.github.webicitybrowser.spec.css.stylesheet.StyleSheetList;

public class DocumentStyleSheetSet {

	private final List<CSSRuleListEntry> uaRules = new ArrayList<>();
	private final StyleSheetList styleSheets;
	
	public DocumentStyleSheetSet(StyleSheetList styleSheets) {
		this.styleSheets = styleSheets;
	}
	
	public List<CSSRuleListEntry> getRuleLists() {
		List<CSSRuleListEntry> ruleLists = new ArrayList<>();
		for (int i = 0; i < styleSheets.getLength(); i++) {
			CSSStyleSheet styleSheet = styleSheets.getItem(i);
			ruleLists.add(new CSSRuleListEntry(styleSheet.getCSSRules(), Source.AUTHOR));
		}
		ruleLists.addAll(uaRules);
		
		return ruleLists;
	};
	
	public void addUARules(CSSRuleList rules) {
		uaRules.add(new CSSRuleListEntry(rules, Source.UA));
	}

	public static record CSSRuleListEntry(CSSRuleList ruleList, Source source) {}
	
}
