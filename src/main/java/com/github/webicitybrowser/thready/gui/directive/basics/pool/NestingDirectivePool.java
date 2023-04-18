package com.github.webicitybrowser.thready.gui.directive.basics.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.ComposedDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;

public class NestingDirectivePool implements ComposedDirectivePool<DirectivePool> {
	
	//TODO: Some sort of cache
	
	private final DirectivePool parent;
	private final DirectivePool defaultPool = new BasicDirectivePool();
	private final List<DirectivePool> subpools = new ArrayList<>(4);
	
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

}