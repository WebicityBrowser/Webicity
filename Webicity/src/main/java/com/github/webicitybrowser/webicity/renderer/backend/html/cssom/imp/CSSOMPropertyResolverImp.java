package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.Optional;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.util.TokenUtils;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMPropertyResolver;

public class CSSOMPropertyResolverImp implements CSSOMPropertyResolver {

	private final CSSOMPropertyResolver parent;
	private final CSSRuleList[] ruleLists;

	public CSSOMPropertyResolverImp(CSSOMPropertyResolver parent, CSSRuleList[] ruleLists) {
		this.parent = parent;
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

	@Override
	public <T> Optional<T> resolveOrInheritProperty(CSSOMPropertyResolverFilter<T> filter) {
		Optional<T> resolvedValue = resolveProperty(filter);
		if (resolvedValue.isEmpty() && parent != null) {
			return parent.resolveOrInheritProperty(filter);
		}

		return resolvedValue;
	}

	// foundImportantProperty is an output parameter (so we don't need a return struct)
	private <T> Optional<T> resolveListProperty(CSSOMPropertyResolverFilter<T> filter, CSSRuleList ruleList, boolean[] foundImportantProperty) {
		Optional<T> result = Optional.empty();
		for (int i = ruleList.getLength() - 1; i >= 0; i--) {
			CSSRule rule = ruleList.getItem(i);
			// TODO: Handle other rule types
			if (!(rule instanceof Declaration declaration)) continue;
			if (!filter.isApplicable(declaration)) continue;
			
			TokenLike[] tokens = preresolveTokens(declaration.getValue());
			if (tokens == null) continue;
			Optional<T> specialResult = checkAndHandleSpecial(filter, tokens);
			Optional<T> declarationResult = specialResult.isPresent() ?
				specialResult :
				filter.filter(declaration.getName(), tokens);
			if (declarationResult.isPresent() && declaration.isImportant()) {
				foundImportantProperty[0] = true;
				return declarationResult;
			}
			if (declarationResult.isPresent() && result.isEmpty()) {
				result = declarationResult;
			}
		}

		return result;
	}

	private <T> Optional<T> checkAndHandleSpecial(CSSOMPropertyResolverFilter<T> filter, TokenLike[] tokens) {
		if (
			tokens.length == 1
			&& tokens[0] instanceof IdentToken identToken
			&& identToken.getValue().equals("inherit")
			&& parent != null
		) {
			return parent.resolveOrInheritProperty(filter);
		}

		return Optional.empty();
	}

	private TokenLike[] preresolveTokens(TokenLike[] tokens) {
		tokens = TokenUtils.stripWhitespace(tokens);
		Optional<TokenLike[]> resolvedTokens = CSSOMVariableResolver.resolveVariables(tokens, this);
		if (resolvedTokens.isEmpty()) return null;

		return resolvedTokens.get();
	}

}
