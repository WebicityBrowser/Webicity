package everyos.desktop.thready.laf.simple.component;

import everyos.desktop.thready.basic.directive.pool.NestingDirectivePool;
import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.ComposedDirectivePool;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.laf.ComponentUI;

public abstract class SimpleComponentUIBase<T extends Component> implements ComponentUI {
	
	// Move directive pool creation to boxing?

	private final T component;
	private final ComponentUI parent;
	
	private ComposedDirectivePool<DirectivePool> directivePool;

	public SimpleComponentUIBase(T component, ComponentUI parent) {
		this.component = component;
		this.parent = parent;
	}

	@Override
	public void invalidate(InvalidationLevel level) {
		parent.invalidate(level);
	}
	
	@Override
	public T getComponent() {
		return this.component;
	}
	
	@Override
	public void release() {
		if (directivePool != null) {
			directivePool.release();
		}
	}
	
	protected ComposedDirectivePool<DirectivePool> setupComposedDirectivePool(DirectivePool parentPool, StyleGenerator generator) {
		if (directivePool != null) {
			directivePool.release();
		}
		
		directivePool = new NestingDirectivePool(parentPool);
		directivePool.addDirectivePool(component.getDirectivePool());
		for (DirectivePool pool: generator.getDirectivePools()) {
			directivePool.addDirectivePool(pool);
		}
		
		return directivePool;
	}

}
