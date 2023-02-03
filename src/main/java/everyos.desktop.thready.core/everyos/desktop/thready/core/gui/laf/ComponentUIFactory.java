package everyos.desktop.thready.core.gui.laf;

import everyos.desktop.thready.core.gui.component.Component;

public interface ComponentUIFactory {
	
	ComponentUI create(Component component, ComponentUI parent);
	
}
