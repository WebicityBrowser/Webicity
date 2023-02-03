package everyos.desktop.thready.core.gui;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.style.StyleGeneratorRoot;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;

public interface Screen {

	void setGUI(Component component, LookAndFeel lookAndFeel, StyleGeneratorRoot styleGeneratorRoot);
	
}
