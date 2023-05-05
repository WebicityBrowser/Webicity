package com.github.webicitybrowser.thready.gui.directive.basics.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.ComposedDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePoolListener;

public class NestingDirectivePool implements ComposedDirectivePool<DirectivePool> {
	
	//TODO: Some sort of cache
	
	private final DirectivePool parent;
	private final DirectivePool defaultPool = new BasicDirectivePool();
	private final List<DirectivePool> subpools = new ArrayList<>(4);
	private final List<DirectivePoolListener> subpoolListeners = new ArrayList<>(4);
	private final Set<DirectivePoolListener> listeners = new HashSet<>(1);
	
	{
		subpools.add(defaultPool);
	}

	public NestingDirectivePool(DirectivePool parent) {
		this.parent = parent;
	}

	@Override
	public DirectivePool directive(Directive directive) {
		defaultPool.directive(directive);
		
		return this;
	}
	
	@Override
	public Optional<Directive> getUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass) {
		Directive directive = searchForDirective(directiveClass);
		
		return Optional.ofNullable(directive);
	}

	@Override
	public Optional<Directive> inheritUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass) {
		Directive directive = searchForDirective(directiveClass);
		
		return inherit(directive, directiveClass);
	}

	@Override
	public Directive getUnresolvedDirective(Class<? extends Directive> directiveClass) {
		return searchForDirective(directiveClass);
	}

	@Override
	public Directive[] getCurrentDirectives() {
		return getAllValues().toArray(new Directive[0]);
	}

	@Override
	public Iterator<Directive> iterator() {
		return getAllValues().iterator();
	}

	@Override
	public void addDirectivePool(DirectivePool pool) {
		DirectivePoolListener listener = new SubpoolListener();
		pool.addEventListener(listener);
		subpools.add(pool);
		subpoolListeners.add(listener);
		fireMassChangeListeners();
	}

	@Override
	public void removeDirectivePool(DirectivePool pool) {
		int removalIndex = subpools.indexOf(pool);
		subpools.remove(removalIndex);
		subpoolListeners.remove(removalIndex);
		fireMassChangeListeners();
	}

	@Override
	public DirectivePool[] getCurrentDirectivePools() {
		return subpools.toArray(new DirectivePool[0]);
	}
	
	@Override
	public void addEventListener(DirectivePoolListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeEventListener(DirectivePoolListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public void release() {
		for (int i = 0; i < subpoolListeners.size(); i++) {
			subpools.get(i).removeEventListener(subpoolListeners.get(i));
		}
	}
	
	private Directive searchForDirective(Class<? extends Directive> directiveClass) {
		for (DirectivePool pool: subpools) {
			Directive directive = pool.getUnresolvedDirective(directiveClass);
			if (directive != null) {
				return directive;
			}
		}
		
		return null;
	}
	
	private Collection<Directive> getAllValues() {
		Map<Class<? extends Directive>, Directive> resolved = new HashMap<>();
		for (DirectivePool pool: subpools) {
			for (Directive directive: pool) {
				resolved.putIfAbsent(directive.getClass(), directive);
			}
		}
		
		return resolved.values();
	}
	
	private Optional<Directive> inherit(Directive directive, Class<? extends Directive> directiveClass) {
		Optional<Directive> optOrEmpty = Optional.ofNullable(directive);
		if (parent == null) {
			return optOrEmpty;
		} else {
			return optOrEmpty
				.or(() -> parent.inheritDirectiveOrEmpty(directiveClass));
		}
	}
	
	private void fireChangeListeners(Directive directive) {
		for (DirectivePoolListener listener: listeners) {
			listener.onDirective(directive);
		}
	}
	
	private void fireMassChangeListeners() {
		for (DirectivePoolListener listener: listeners) {
			listener.onMassChange();
		}
	}
	
	private class SubpoolListener implements DirectivePoolListener {
		@Override
		public void onMassChange() {
			fireMassChangeListeners();
		}
		
		@Override
		public void onDirective(Directive directive) {
			fireChangeListeners(directive);
		}
	};

}