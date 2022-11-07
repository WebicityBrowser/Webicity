package everyos.desktop.thready.basic.directive;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
		// TODO Resolve unresolved directives
		return (T) directives.get(directiveClass);
	}

	@Override
	public <T extends Directive> T getDirectiveOrDefault(Class<T> directiveClass, T def) {
		if (directives.containsKey(directiveClass)) {
			return getDirective(directiveClass);
		}
		return def;
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
