package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.webicitybrowser.performance.LazyMap;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePoolListener;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMPropertyResolver;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMPropertyResolver.CSSOMPropertyResolverFilter;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class DocumentDirectivePool implements DirectivePool {

	private final DirectivePool parentPool;
	private final CSSOMPropertyResolver propertyResolver;
	private final CSSOMDeclarationParser declarationParser;

	private final Map<Class<? extends Directive>, Optional<Directive>> directiveCache = new LazyNormalHashMap<>();

	public DocumentDirectivePool(DirectivePool parentPool, CSSOMPropertyResolver propertyResolver, CSSOMDeclarationParser declarationParser) {
		this.parentPool = parentPool;
		this.propertyResolver = propertyResolver;
		this.declarationParser = declarationParser;
	}

	@Override
	public DirectivePool directive(Directive directive) {
		directiveCache.put(directive.getClass(), Optional.of(directive));

		return this;
	}

	@Override
	public Optional<Directive> getUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass) {
		return directiveCache.computeIfAbsent(directiveClass, this::resolveDirective);
	}

	@Override
	public Optional<Directive> inheritUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass) {
		Optional<Directive> result = getUncastedDirectiveOrEmpty(directiveClass);
		if (result.isEmpty() && parentPool != null) {
			return parentPool.inheritUncastedDirectiveOrEmpty(directiveClass);
		}

		return result;
	}

	@Override
	public Directive getUnresolvedDirective(Class<? extends Directive> directiveClass) {
		// TODO: Should this method just be removed?
		return getUncastedDirectiveOrEmpty(directiveClass).orElseThrow();
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

	private Optional<Directive> resolveDirective(Class<? extends Directive> directiveClass) {
		return propertyResolver.resolveProperty(new DocumentPropertyResolverFilter(directiveClass));
	}

	private class DocumentPropertyResolverFilter implements CSSOMPropertyResolverFilter<Directive> {

		private final Class<? extends Directive> directiveClass;

		public DocumentPropertyResolverFilter(Class<? extends Directive> directiveClass) {
			this.directiveClass = directiveClass;
		}

		@Override
		public boolean isApplicable(Declaration propertyValue) {
			CSSOMNamedDeclarationParser<?> namedParser = declarationParser.getNamedDeclarationParser(propertyValue.getName());
			return namedParser != null && namedParser.getResultantDirectiveClasses().contains(directiveClass);
		}

		@Override
		public Optional<Directive> filter(String name, TokenLike[] tokens) {
			Directive[] results = declarationParser.parseDeclaration(name, tokens);
			for (Directive result: results) {
				if (directiveClass.isInstance(result)) {
					return Optional.of(result);
				}
			}

			return Optional.empty();
		}
		
	}

	private static class LazyNormalHashMap<K, V> extends LazyMap<K, V> {
		@Override
		protected Map<K, V> initialize() {
			return new HashMap<>(1);
		}
	}
	
}
