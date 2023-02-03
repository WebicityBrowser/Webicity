package everyos.desktop.thready.basic.component;

import java.util.ArrayList;
import java.util.List;

import everyos.desktop.thready.basic.directive.pool.BasicDirectivePool;
import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.Directive;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.laf.ComponentUI;

public abstract class ComponentBase implements Component {
	
	private final DirectivePool directivePool = new BasicDirectivePool();
	private final List<ComponentUI> boundUIs = new ArrayList<>();

	@Override
	public DirectivePool getDirectivePool() {
		return directivePool;
	}

	@Override
	public Component directive(Directive directive) {
		directivePool.directive(directive);
		
		return this;
	}

	@Override
	public void invalidate(InvalidationLevel level) {
		for (ComponentUI ui: boundUIs) {
			ui.invalidate(level);
		}
	}

	@Override
	public void bindUI(ComponentUI ui) {
		boundUIs.add(ui);
	}

	@Override
	public void unbindUI(ComponentUI ui) {
		boundUIs.remove(ui);
	}

}
