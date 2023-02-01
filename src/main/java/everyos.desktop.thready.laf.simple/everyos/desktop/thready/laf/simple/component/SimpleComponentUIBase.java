package everyos.desktop.thready.laf.simple.component;

import everyos.desktop.thready.basic.directive.pool.NestingDirectivePool;
import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.ComposedDirectivePool;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;

public abstract class SimpleComponentUIBase<T extends Component> implements ComponentUI {

	private final T component;
	private final ComponentUI parent;
	private final ComposedDirectivePool<DirectivePool> directivePool;

	public SimpleComponentUIBase(T component, ComponentUI parent) {
		this.component = component;
		this.parent = parent;
		this.directivePool = setupComposedDirectivePool();
	}

	@Override
	public void invalidate(InvalidationLevel level) {
		parent.invalidate(level);
	}

	@Override
	public ComposedDirectivePool<?> getComputedDirectives() {
		return directivePool;
	}
	
	@Override
	public T getComponent() {
		return this.component;
	}
	
	private ComposedDirectivePool<DirectivePool> setupComposedDirectivePool() {
		ComposedDirectivePool<DirectivePool> pool = new NestingDirectivePool();
		pool.addDirectivePool(component.getDirectivePool());
		
		return pool;
	}

}
