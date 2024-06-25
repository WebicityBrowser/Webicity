package com.github.webicitybrowser.thready.gui.directive.basics.pool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

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
	@SuppressWarnings("unchecked")
	public <T extends Directive> Optional<T> getDirectiveOrEmpty(Class<T> directiveClass) {
		T directive = (T) searchForDirective(directiveClass);
		
		return Optional.ofNullable(directive);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Directive> Optional<T> inheritDirectiveOrEmpty(Class<T> directiveClass) {
		T directive = (T) searchForDirective(directiveClass);
		
		return inherit(directive, directiveClass);
	};

	@Override
	public <T extends Directive> T derive(Class<T> directiveClass, BiFunction<DirectivePool, DirectivePool, T> deriveFunction) {
		// TODO: Invalidate
		Optional<T> optOrEmpty = getDirectiveOrEmpty(directiveClass);
		if (optOrEmpty.isPresent()) return optOrEmpty.get();
		T result = deriveFunction.apply(this, parent);
		directive(result);
		return result;
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
			Directive directive = pool.getDirectiveOrEmpty(directiveClass).orElse(null);
			if (directive != null) {
				return directive;
			}
		}
		
		return null;
	}
	
	private <T extends Directive> Optional<T> inherit(T directive, Class<T> directiveClass) {
		Optional<T> optOrEmpty = Optional.ofNullable(directive);
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
	}

}