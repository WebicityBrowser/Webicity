package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import java.util.Optional;

import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMPropertyResolver;

public class CSSOMPropertyResolverImp implements CSSOMPropertyResolver {

	private final CSSRuleList[] ruleLists;

	public CSSOMPropertyResolverImp(CSSOMPropertyResolver parent, CSSRuleList[] ruleLists) {
		this.ruleLists = ruleLists;
	}

	@Override
	public <T> Optional<T> resolveProperty(CSSOMPropertyResolverFilter<T> filter) {
		Optional<T> result = Optional.empty();
		boolean[] foundImportantProperty = new boolean[] { false };
		for (CSSRuleList ruleList: ruleLists) {
			Optional<T> listResult = resolveListProperty(filter, ruleList, foundImportantProperty);
			if (listResult.isPresent() && result.isEmpty()) {
				result = listResult;
			}
			if (foundImportantProperty[0]) {
				return listResult;
			}
		}

		return result;
	}

	// foundImportantProperty is an output parameter (so we don't need a return struct)
	private <T> Optional<T> resolveListProperty(CSSOMPropertyResolverFilter<T> filter, CSSRuleList ruleList, boolean[] foundImportantProperty) {
		Optional<T> result = Optional.empty();
		for (int i = ruleList.getLength() - 1; i >= 0; i--) {
			CSSRule rule = ruleList.getItem(i);
			// TODO: Handle other rule types
			if (!(rule instanceof Declaration declaration)) continue;

			Optional<T> declarationResult = filter.filter(declaration);
			if (declarationResult.isPresent() && declaration.isImportant()) {
				foundImportantProperty[0] = true;
				return declarationResult;
			}
			if (declarationResult.isPresent()) {
				result = declarationResult;
			}
		}

		return result;
	}

}
