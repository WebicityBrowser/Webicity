package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.util.TokenUtils;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMMappedRuleList;

public class CSSOMMappedRuleListImp<T> implements CSSOMMappedRuleList<T> {

	private final PropertyMapper<T> propertyMapper;

	private final HashMap<Object, PropertyMeta<T>> resolvedProperties = new HashMap<>();

	public CSSOMMappedRuleListImp(CSSRuleList ruleList, PropertyMapper<T> propertyMapper) {
		this.propertyMapper = propertyMapper;
		recomputeProperties(ruleList);
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public <U extends T> PropertyMeta<U> resolveProperty(Class<U> propertyType, RelativeResolver<T> relativeResolver) {
		PropertyMeta<T> resolvedPropertyMeta = followFallbackChain((PropertyMeta) resolvedProperties.get(propertyType), relativeResolver, propertyType);
		if (resolvedPropertyMeta == null) {
			return (PropertyMeta) PropertyMeta.EMPTY;
		}

		return (PropertyMeta<U>) resolvedPropertyMeta;
	}

	@Override
	public Optional<TokenLike[]> resolveVariable(String variableName, RelativeResolver<T> relativeResolver) {
		PropertyMeta<T> resolvedPropertyMeta = followFallbackChain(resolvedProperties.get(variableName), relativeResolver, null);
		if (resolvedPropertyMeta == null || resolvedPropertyMeta.tokens() == null) {
			return Optional.empty();
		}
		
		return Optional.of(resolvedPropertyMeta.tokens());
	}

	private void recomputeProperties(CSSRuleList ruleList) {
		resolvedProperties.clear();
		for (int i = 0; i < ruleList.getLength(); i++) {
			CSSRule rule = ruleList.getItem(i);
			// TODO: Handle other rule types
			if (!(rule instanceof Declaration declaration)) continue;
			// TODO: Support var types
			TokenLike[] tokens = TokenUtils.stripWhitespace(declaration.getValue());
			if (isNonCachedProperty(declaration, tokens)) {
				PropertyMeta<T> propertyMeta = new PropertyMeta<T>(null, declaration.getName(), tokens, declaration.isImportant(), false, null);
				maybeAddPropertyValue(declaration, propertyMeta);
				continue;
			}
			List<T> propertyValues = propertyMapper.map(declaration.getName(), tokens);
			for (T propertyValue: propertyValues) {
				PropertyMeta<T> propertyMeta = new PropertyMeta<T>(propertyValue, declaration.getName(), tokens, declaration.isImportant(), true, null);
				maybeAddPropertyValue(declaration, propertyMeta);
			}
		}
	}

	private void maybeAddPropertyValue(Declaration declaration, PropertyMeta<T> propertyMeta) {
		PropertyMeta<T> oldMeta = resolvedProperties.get(declaration.getName());
		if (!declaration.isImportant() && oldMeta != null && oldMeta.important()) {
			if (oldMeta.present()) return;
			resolvedProperties.put(declaration.getName(), oldMeta.fallback());
			maybeAddPropertyValue(declaration, propertyMeta);
			PropertyMeta<T> newFallbackMeta = resolvedProperties.get(declaration.getName());
			PropertyMeta<T> oldMetaWithNewFallback = new PropertyMeta<>(
				oldMeta.resolvedValue(), oldMeta.name(), oldMeta.tokens(), oldMeta.important(), oldMeta.cacheable(), newFallbackMeta);
			resolvedProperties.put(declaration.getName(), oldMetaWithNewFallback);

			return;
		}

		PropertyMeta<T> newMeta = new PropertyMeta<>(
			propertyMeta.resolvedValue(), propertyMeta.name(), propertyMeta.tokens(), declaration.isImportant(), propertyMeta.cacheable(), oldMeta);

		if (propertyMeta.present()) {
			resolvedProperties.put(propertyMapper.keyForValue(propertyMeta.resolvedValue()), newMeta);
		} else if (declaration.getName().startsWith("--")) {
			resolvedProperties.put(declaration.getName(), newMeta);
		} else {
			for (Class<? extends T> possibleType: propertyMapper.getPossibleResultantTypes(declaration.getName())) {
				resolvedProperties.put(possibleType, newMeta);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <U extends T> PropertyMeta<U> followFallbackChain(PropertyMeta<U> propertyMeta, RelativeResolver<T> relativeResolver, Class<U> propertyType) {
		if (propertyMeta == null || propertyMeta.present()) return propertyMeta;
		PropertyMeta<U> currentMeta = new PropertyMeta<U>(null, null, null, false, false, propertyMeta);
		while (true) {
			currentMeta = currentMeta.fallback();
			if (currentMeta == null) return null;
			if (propertyType != null && currentMeta.present()) return (PropertyMeta) currentMeta;
			if (currentMeta.tokens() == null) continue;
			Optional<TokenLike[]> resolvedTokens = CSSOMVariableResolver.resolveVariables(currentMeta.tokens(), relativeResolver);
			if (resolvedTokens.isEmpty()) continue;
			if (propertyType == null) return new PropertyMeta(null, null, resolvedTokens.get(), currentMeta.important(), false, currentMeta.fallback());
			PropertyMeta<U> resolvedSpecial = resolveSpecialProperty(resolvedTokens.get(), relativeResolver, propertyType);
			if (resolvedSpecial.present()) return (PropertyMeta) resolvedSpecial;
			List<T> propertyValues = propertyMapper.map(currentMeta.name(), resolvedTokens.get());
			if (propertyValues.isEmpty()) continue;
			for (T propertyValue: propertyValues) {
				if (propertyMapper.keyForValue(propertyValue) == propertyType) {
					return new PropertyMeta<>((U) propertyValue, propertyMeta.name(), resolvedTokens.get(), currentMeta.important(), false, currentMeta.fallback());
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <U extends T> PropertyMeta<U> resolveSpecialProperty(TokenLike[] tokenLikes, RelativeResolver<T> relativeResolver, Class<U> propertyType) {
		if (isInherit(tokenLikes)) {
			return relativeResolver.resolveParentProperty(propertyType);
		}

		return (PropertyMeta) PropertyMeta.EMPTY;
	}

	private boolean isNonCachedProperty(Declaration declaration, TokenLike[] tokens) {
		boolean isVariable = declaration.getName().startsWith("--");
		return isInherit(tokens) || isVariable || CSSOMVariableResolver.hasVariable(tokens);
	}

	private boolean isInherit(TokenLike[] tokens) {
		return tokens.length == 1
			&& tokens[0] instanceof IdentToken identToken
			&& identToken.getValue().equals("inherit");
	}
	
}
