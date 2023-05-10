package com.github.webicitybrowser.spec.css.rule.imp;

import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;

public class CSSRuleListImp implements CSSRuleList {

	private final CSSRule[] rules;

	public CSSRuleListImp(CSSRule[] rules) {
		this.rules = rules;
	}

	@Override
	public CSSRule getItem(int index) {
		return rules[index];
	}

	@Override
	public int getLength() {
		return rules.length;
	}

}
