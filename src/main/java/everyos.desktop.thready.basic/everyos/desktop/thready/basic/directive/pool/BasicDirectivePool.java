package everyos.desktop.thready.basic.directive.pool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import everyos.desktop.thready.core.gui.directive.Directive;
import everyos.desktop.thready.core.gui.directive.DirectivePool;

public class BasicDirectivePool implements DirectivePool {
	
	private Map<Class<? extends Directive>, Directive> directives = new HashMap<>();

	@Override
	public Iterator<Directive> iterator() {
		return directives.values().iterator();
	}

	@Override
	public DirectivePool directive(Directive directive) {
		directives.put(directive.getDirectiveClass(), directive);
		
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Directive> T getDirective(Class<T> directiveClass) {
		return (T) directives.get(directiveClass);
	}

	@Override
	public <T extends Directive> Optional<T> getDirectiveOrEmpty(Class<T> directiveClass) {
		return Optional.ofNullable(getDirective(directiveClass));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Directive> T getRawDirective(Class<T> directiveClass) {
		return (T) directives.get(directiveClass);
	}

	@Override
	public Directive[] getCurrentDirectives() {
		return directives.values().toArray(new Directive[0]);
	}

}
