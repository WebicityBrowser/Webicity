package everyos.engine.ribbon.core.ui;

import everyos.engine.ribbon.core.graphics.Component;

public interface ComponentUIFactory {
	
	ComponentUI create(Component component, ComponentUI parent);
	
}
