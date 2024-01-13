package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.Optional;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePoolListener;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMPropertyResolver;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMPropertyResolver.CSSOMPropertyResolverFilter;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public class DocumentDirectivePool implements DirectivePool {

	private final DirectivePool parentPool;
	private final CSSOMPropertyResolver propertyResolver;
	private final CSSOMDeclarationParser declarationParser;

	private final DocumentDirectivePoolCache directiveCache = new DocumentDirectivePoolCache();

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
	public <T extends Directive> Optional<T> getDirectiveOrEmpty(Class<T> directiveClass) {
		return (Optional<T>) directiveCache.computeIfAbsent(directiveClass, this::resolveDirective);
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

	private <T extends Directive> Optional<T> resolveDirective(Class<T> directiveClass) {
		return propertyResolver.resolveProperty(new DocumentPropertyResolverFilter<>(directiveClass));
	}

	private class DocumentPropertyResolverFilter<T> implements CSSOMPropertyResolverFilter<T> {

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
		@SuppressWarnings("unchecked")
		public Optional<T> filter(String name, TokenLike[] tokens) {
			Directive[] results = declarationParser.parseDeclaration(name, tokens);
			for (Directive result: results) {
				if (directiveClass.isInstance(result)) {
					return Optional.of((T) result);
				}
			}

			return Optional.empty();
		}
		
	}
	
}
