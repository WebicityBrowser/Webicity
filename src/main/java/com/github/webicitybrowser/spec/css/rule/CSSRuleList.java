package com.github.webicitybrowser.spec.css.rule;

import com.github.webicitybrowser.spec.css.rule.imp.CSSRuleListImp;

public interface CSSRuleList {

	CSSRule getItem(int index);
	
	// TODO: Must support values greater than MAX_INT
	int getLength();

	static CSSRuleList create(CSSRule[] rules) {
		return new CSSRuleListImp(rules);
	}
	
}
