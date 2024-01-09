package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMPropertyResolver;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMRuleMap;

public class CSSOMPropertyResolverImp implements CSSOMPropertyResolver {

	private final CSSOMRuleMap[] ruleMaps;

	public CSSOMPropertyResolverImp(CSSOMPropertyResolver parent, CSSOMRuleMap[] ruleMaps) {
		this.ruleMaps = ruleMaps;
	}

	public TokenLike[] resolveProperty(String propertyName) {
		Declaration chosenDeclaration = chooseDeclaration(propertyName);

		return chosenDeclaration.getValue();
	}

	private Declaration chooseDeclaration(String propertyName) {
		Declaration chosenDeclaration = null;
		boolean onlyAllowImportant = false;
		for (CSSOMRuleMap ruleMap: ruleMaps) {
			Declaration declaration = ruleMap.getDeclaration(propertyName);
			if (onlyAllowImportant && declaration.isImportant()) {
				return declaration;
			} else if (!onlyAllowImportant) {
				chosenDeclaration = declaration;
				onlyAllowImportant = true;
			}
		}

		return chosenDeclaration;
	}

}
