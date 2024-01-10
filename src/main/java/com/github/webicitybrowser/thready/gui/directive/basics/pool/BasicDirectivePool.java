package com.github.webicitybrowser.thready.gui.directive.basics.pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.webicitybrowser.performance.LazyMap;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePoolListener;

/**
 * A simple directive pool with no inheritance or other
 * additional features.
 */
public class BasicDirectivePool implements DirectivePool {

	private final Map<Class<? extends Directive>, Directive> directives = new LazyNormalHashMap<>();
	private final List<DirectivePoolListener> listeners = new ArrayList<>(1);

	@Override
	public DirectivePool directive(Directive directive) {
		directives.put(directive.getPrimaryType(), directive);
		fireChangeListeners(directive);
		
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
	public void addEventListener(DirectivePoolListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeEventListener(DirectivePoolListener listener) {
		listeners.remove(listener);
	}
	
	private void fireChangeListeners(Directive directive) {
		for (DirectivePoolListener listener: listeners) {
			listener.onDirective(directive);
		}
	}

	private static class LazyNormalHashMap<K, V> extends LazyMap<K, V> {
		@Override
		protected Map<K, V> initialize() {
			return new HashMap<>(1);
		}
	}
	
}
