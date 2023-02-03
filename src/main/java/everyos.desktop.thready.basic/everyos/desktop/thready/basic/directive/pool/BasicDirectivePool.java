package everyos.desktop.thready.basic.directive.pool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import everyos.desktop.thready.core.gui.directive.Directive;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.directive.DirectivePoolListener;
import everyos.desktop.thready.core.gui.directive.HotDirective;

public class BasicDirectivePool implements DirectivePool {
	
	private final Map<Class<? extends Directive>, Directive> directives = new HashMap<>();
	private final List<DirectivePoolListener> listeners = new ArrayList<>(1);

	@Override
	public Iterator<Directive> iterator() {
		return directives.values().iterator();
	}

	@Override
	public DirectivePool directive(Directive directive) {
		Directive oldDirective = directives.get(directive.getDirectiveClass());
		if (oldDirective instanceof HotDirective) {
			((HotDirective) oldDirective).unbind(this);
		}
		
		directives.put(directive.getDirectiveClass(), directive);
		if (directive instanceof HotDirective) {
			((HotDirective) directive).bind(this);
		}
		
		fireChangeListeners(directive.getDirectiveClass());
		
		return this;
	}

	@Override
	public Optional<Directive> getUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass) {
		Directive directive = directives.get(directiveClass);
		if (directive instanceof HotDirective) {
			directive = ((HotDirective) directive).getCurrentDirective();
		}
		
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
