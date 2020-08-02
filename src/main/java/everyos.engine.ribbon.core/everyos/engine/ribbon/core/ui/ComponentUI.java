package everyos.engine.ribbon.core.ui;

import everyos.engine.ribbon.core.component.Component;

public interface  ComponentUI {
	public ComponentUI create(Component c, ComponentUI parent);
	public void directive(UIDirective directive);
	public void invalidate();
}
