package everyos.desktop.thready.basic.directive.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import everyos.desktop.thready.core.gui.directive.ComposedDirectivePool;
import everyos.desktop.thready.core.gui.directive.Directive;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.directive.DirectivePoolListener;
import everyos.desktop.thready.core.gui.directive.InheritDirective;

public class NestingDirectivePool implements ComposedDirectivePool<DirectivePool> {
	
	//TODO: Some sort of cache
	
	private final DirectivePool parent;
	private final DirectivePool defaultPool = new BasicDirectivePool();
	private final List<DirectivePool> subpools = new ArrayList<>(4);
	private final List<DirectivePoolListener> subpoolListeners = new ArrayList<>(4);
	private final List<DirectivePoolListener> listeners = new ArrayList<>(1);
	
	{
		subpools.add(defaultPool);
	}

	public NestingDirectivePool(DirectivePool parent) {
		if (parent != null && parent instanceof BasicDirectivePool) {
			throw new RuntimeException();
		}
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
		if (directive instanceof InheritDirective) {
			return inherit(null, directiveClass);
		}
		
		return Optional.ofNullable(directive);
	}

	@Override
	public Optional<Directive> inheritUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass) {
		Directive directive = searchForDirective(directiveClass);
		if (directive instanceof InheritDirective) {
			directive = null;
		}
		
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
	
	private void fireChangeListeners(Class<? extends Directive> directiveCls) {
		for (DirectivePoolListener listener: listeners) {
			listener.onDirective(directiveCls);
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
		public void onDirective(Class<? extends Directive> directiveCls) {
			fireChangeListeners(directiveCls);
		}
	};

}
