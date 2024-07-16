package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.DirectiveDeriver;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePoolListener;
import com.github.webicitybrowser.threadyweb.graphical.directive.derived.DerivedFontDirective;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMMappedRuleList;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMMappedRuleList.PropertyMeta;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMMappedRuleList.RelativeResolver;

public class DocumentDirectivePool implements DirectivePool, RelativeResolver<Directive> {

	private final DocumentDirectivePool parentPool;
	private final List<CSSOMMappedRuleList<Directive>> mappedRuleLists;
	private final Map<Class<? extends Directive>, DirectiveDeriver<? extends Directive>> derivers;

	// TODO: Better way to cache this
	private DerivedFontDirective fontDirective;

	public DocumentDirectivePool(
		DocumentDirectivePool parentPool, List<CSSOMMappedRuleList<Directive>> mappedRuleLists,
		Map<Class<? extends Directive>, DirectiveDeriver<? extends Directive>> derivers
	) {
		this.parentPool = parentPool;
		this.mappedRuleLists = mappedRuleLists;
		this.derivers = derivers;
	}

	@Override
	public DirectivePool directive(Directive directive) {
		throw new UnsupportedOperationException("Unimplemented method 'directive'");
	}

	@Override
	@SuppressWarnings("unchecked")
	
	public <T extends Directive> Optional<T> getDirectiveOrEmpty(Class<T> directiveClass) {
		if (directiveClass == DerivedFontDirective.class && fontDirective != null) {
			return Optional.of((T) fontDirective);
		}
		return resolveDirective(directiveClass);
	}

	@Override
	public <T extends Directive> Optional<T> inheritDirectiveOrEmpty(Class<T> directiveClass) {
		Optional<T> result = getDirectiveOrEmpty(directiveClass);
		if (result.isEmpty() && parentPool != null) {
			return parentPool.inheritDirectiveOrEmpty(directiveClass);
		}

		return result;
	}

	@Override
	public void addEventListener(DirectivePoolListener listener) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addEventListener'");
	}

	@Override
	public void removeEventListener(DirectivePoolListener listener) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeEventListener'");
	}

	@SuppressWarnings("unchecked")
	private <T extends Directive> Optional<T> resolveDirective(Class<T> directiveClass) {
		// TODO: Invalidate derivations
		if (derivers.containsKey(directiveClass)) {
			DirectiveDeriver<T> deriver = (DirectiveDeriver<T>) derivers.get(directiveClass);
			Optional<T> derived = deriver.derive(this, parentPool);
			if (derived.isPresent()) {
				if (directiveClass == DerivedFontDirective.class) {
					fontDirective = (DerivedFontDirective) derived.get();
				}
				
				return derived;
			}
		}

		PropertyMeta<T> resolvedPropertyMeta = null;
		for (CSSOMMappedRuleList<Directive> mappedRuleList: mappedRuleLists) {
			PropertyMeta<T> propertyMeta = mappedRuleList.resolveProperty(directiveClass, this);
			if (propertyMeta.present() && (propertyMeta.important() || resolvedPropertyMeta == null)) {
				resolvedPropertyMeta = propertyMeta;
			}
			if (propertyMeta.present() && propertyMeta.important()) break;
		}

		if (resolvedPropertyMeta == null) return Optional.empty();
		return Optional.of(resolvedPropertyMeta.resolvedValue());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <U extends Directive> PropertyMeta<U> resolveParentProperty(Class<U> propertyType) {
		if (parentPool == null) {
			return (PropertyMeta) PropertyMeta.EMPTY;
		}

		U property = parentPool.getDirectiveOrEmpty(propertyType).orElse(null);
		if (property == null) {
			return (PropertyMeta) PropertyMeta.EMPTY;
		}

		return new PropertyMeta<U>(property, null, null, false, false, null);
	}

	@Override
	public Optional<TokenLike[]> resolveVariable(String variableName) {
		for (CSSOMMappedRuleList<Directive> mappedRuleList: mappedRuleLists) {
			Optional<TokenLike[]> variableValue = mappedRuleList.resolveVariable(variableName, this);
			if (variableValue.isPresent()) {
				return variableValue;
			}
		}

		return parentPool != null ? parentPool.resolveVariable(variableName) : Optional.empty();
	}
	
}
