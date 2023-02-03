package everyos.desktop.thready.core.gui.laf;

import everyos.desktop.thready.core.gui.component.Component;

public interface LookAndFeel {

	ComponentUI createUIFor(Component component, ComponentUI parent);
	
}
