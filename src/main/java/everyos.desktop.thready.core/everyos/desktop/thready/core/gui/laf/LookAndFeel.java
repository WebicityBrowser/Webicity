package everyos.desktop.thready.core.gui.laf;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;

public interface LookAndFeel {

	ComponentUI createUIFor(Component component);
	
}
