package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.List;
import java.util.Optional;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp.CSSOMMappedRuleListImp;

public interface CSSOMMappedRuleList<T> {
	
	<U extends T> PropertyMeta<U> resolveProperty(Class<U> propertyType, RelativeResolver<T> unknownResolver);

	Optional<TokenLike[]> resolveVariable(String variableName, RelativeResolver<T> unknownResolver);

	interface RelativeResolver<T> {

		<U extends T> PropertyMeta<U> resolveParentProperty(Class<U> propertyType);

		Optional<TokenLike[]> resolveVariable(String variableName);

	}

	record PropertyMeta<T>(T resolvedValue, String name, TokenLike[] tokens, boolean important, boolean cacheable, PropertyMeta<T> fallback) {

		public static PropertyMeta<?> EMPTY = new PropertyMeta<>(null, null, null, false, false, null);
	
		public boolean present() {
			return resolvedValue != null;
		}
	
	}	

	interface PropertyMapper<T> {
		List<Class<? extends T>> getPossibleResultantTypes(String name);
		List<T> map(String name, TokenLike[] tokens);
		Class<? extends T> keyForValue(T value);
	}

	public static <T> CSSOMMappedRuleList<T> create(CSSRuleList ruleList, PropertyMapper<T> propertyMapper) {
		return new CSSOMMappedRuleListImp<>(ruleList, propertyMapper);
	}

}
