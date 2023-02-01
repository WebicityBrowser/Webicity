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

public class NestingDirectivePool implements ComposedDirectivePool<DirectivePool> {
	
	private final DirectivePool defaultPool = new BasicDirectivePool();
	private final List<DirectivePool> subpools = new ArrayList<>(4);
	
	{
		subpools.add(defaultPool);
	}

	@Override
	public DirectivePool directive(Directive directive) {
		defaultPool.directive(directive);
		
		return this;
	}

	@Override
	// TODO: Ability to inherit
	public <T extends Directive> T getDirective(Class<T> directiveClass) {
		return searchForDirective(directiveClass);
	}

	@Override
	public <T extends Directive> Optional<T> getDirectiveOrEmpty(Class<T> directiveClass) {
		return Optional.ofNullable(searchForDirective(directiveClass));
	}

	@Override
	public <T extends Directive> T getRawDirective(Class<T> directiveClass) {
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
		subpools.add(pool);
	}

	@Override
	public void removeDirectivePool(DirectivePool pool) {
		subpools.remove(pool);
	}

	@Override
	public DirectivePool[] getCurrentDirectivePools() {
		return subpools.toArray(new DirectivePool[0]);
	}
	
	private <T extends Directive> T searchForDirective(Class<T> directiveClass) {
		for (DirectivePool pool: subpools) {
			T directive = pool.getDirective(directiveClass);
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

}
