package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.Iterator;
import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePoolListener;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMPropertyResolver;

public class DocumentDirectivePool implements DirectivePool {

	private final CSSOMPropertyResolver propertyResolver;
	private final CSSOMDeclarationParser declarationParser;

	public DocumentDirectivePool(CSSOMPropertyResolver propertyResolver, CSSOMDeclarationParser declarationParser) {
		this.propertyResolver = propertyResolver;
		this.declarationParser = declarationParser;
	}

	@Override
	public Iterator<Directive> iterator() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'iterator'");
	}

	@Override
	public DirectivePool directive(Directive directive) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'directive'");
	}

	@Override
	public Optional<Directive> getUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getUncastedDirectiveOrEmpty'");
	}

	@Override
	public Optional<Directive> inheritUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'inheritUncastedDirectiveOrEmpty'");
	}

	@Override
	public Directive[] getCurrentDirectives() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getCurrentDirectives'");
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
