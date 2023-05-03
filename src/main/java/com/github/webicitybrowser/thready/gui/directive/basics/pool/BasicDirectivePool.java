package com.github.webicitybrowser.thready.gui.directive.basics.pool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePoolListener;

/**
 * A simple directive pool with no inheritance or other
 * additional features.
 */
public class BasicDirectivePool implements DirectivePool {

	private final Map<Class<? extends Directive>, Directive> directives = new HashMap<>(4);
	private final Set<DirectivePoolListener> listeners = new HashSet<>(1);
	
	@Override
	public Iterator<Directive> iterator() {
		return directives.values().iterator();
	}

	@Override
	public DirectivePool directive(Directive directive) {
		directives.put(directive.getPrimaryType(), directive);
		fireChangeListeners(directive.getPrimaryType());
		
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

	@Override
	public void addEventListener(DirectivePoolListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeEventListener(DirectivePoolListener listener) {
		listeners.remove(listener);
	}
	
	private void fireChangeListeners(Class<? extends Directive> directiveCls) {
		for (DirectivePoolListener listener: listeners) {
			listener.onDirective(directiveCls);
		}
	}

}
