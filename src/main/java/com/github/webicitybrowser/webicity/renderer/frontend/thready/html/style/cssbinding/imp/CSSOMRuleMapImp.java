package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMRuleMap;

public class CSSOMRuleMapImp implements CSSOMRuleMap {

	private final Declaration[] declarations;

	public CSSOMRuleMapImp(Declaration[] declarations) {
		this.declarations = declarations;
	}

	@Override
	public Declaration getDeclaration(String propertyName) {
		// TODO: Should we use a hashmap or binary search (with sorted chars) or something
		// else to speed this up?
		for (Declaration declaration: declarations) {
			if (declaration.getName().equals(propertyName)) {
				return declaration;
			}
		}

		return null;
	}
	
}
