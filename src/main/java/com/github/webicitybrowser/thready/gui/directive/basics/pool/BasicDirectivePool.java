package com.github.webicitybrowser.thready.gui.directive.basics.pool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;

/**
 * A simple directive pool with no inheritance or other
 * additional features.
 */
public class BasicDirectivePool implements DirectivePool {

	private final Map<Class<? extends Directive>, Directive> directives = new HashMap<>();

	@Override
	public Iterator<Directive> iterator() {
		return directives.values().iterator();
	}

	@Override
	public DirectivePool directive(Directive directive) {
		directives.put(directive.getPrimaryType(), directive);
		
		return this;
	}

	@Override
	public Optional<Directive> getUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass) {
		Directive directive = directives.get(directiveClass);
		return Optional.ofNullable(directive);
	}
	
	@Override
	public Optional<Directive> inheritUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass) {
		return getUncastedDirectiveOrEmpty(directiveClass);
	}

	@Override
	public Directive getUnresolvedDirective(Class<? extends Directive> directiveClass) {
		return directives.get(directiveClass);
	}

	@Override
	public Directive[] getCurrentDirectives() {
		return directives.values().toArray(new Directive[0]);
	}

}
