package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePoolListener;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMPropertyResolver;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class DocumentDirectivePool implements DirectivePool {

	private final DirectivePool parentPool;
	private final CSSOMPropertyResolver propertyResolver;
	private final CSSOMDeclarationParser declarationParser;

	public DocumentDirectivePool(DirectivePool parentPool, CSSOMPropertyResolver propertyResolver, CSSOMDeclarationParser declarationParser) {
		this.parentPool = parentPool;
		this.propertyResolver = propertyResolver;
		this.declarationParser = declarationParser;
	}

	@Override
	public DirectivePool directive(Directive directive) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'directive'");
	}

	@Override
	public Optional<Directive> getUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass) {
		Optional<Directive> r = propertyResolver.resolveProperty(value -> {
			CSSOMNamedDeclarationParser<?> namedParser = declarationParser.getNamedDeclarationParser(value.getName());
			if (namedParser == null || !namedParser.getResultantDirectiveClasses().contains(directiveClass)) {
				return Optional.empty();
			}

			Directive[] results = declarationParser.parseDeclaration(value);
			for (Directive result: results) {
				if (directiveClass.isInstance(result)) {
					return Optional.of(result);
				}
			}

			return Optional.empty();
		});

		return r;
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getUnresolvedDirective'");
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
	
}
