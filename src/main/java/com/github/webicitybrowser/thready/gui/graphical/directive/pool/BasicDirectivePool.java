package com.github.webicitybrowser.thready.gui.graphical.directive.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;

public class BasicDirectivePool implements DirectivePool {

	private final Map<Class<? extends Directive>, Directive> directives = new HashMap<>();
	
	@Override
	public DirectivePool directive(Directive directive) {
		directives.put(directive.getPrimaryType(), directive);
		
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Directive> Optional<T> getDirectiveOrEmpty(Class<T> directiveCls) {
		return Optional.ofNullable((T) directives.get(directiveCls));
	}

	@Override
	public <T extends Directive> Optional<T> inheritDirectiveOrEmpty(Class<T> directiveCls) {
		return getDirectiveOrEmpty(directiveCls);
	}

}
