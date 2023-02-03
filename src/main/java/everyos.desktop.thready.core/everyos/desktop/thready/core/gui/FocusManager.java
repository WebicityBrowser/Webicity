package everyos.desktop.thready.core.gui;

import everyos.desktop.thready.core.gui.laf.ComponentUI;

public interface FocusManager {

	void setFocusedUI(ComponentUI ui);
	
	ComponentUI getFocusedUI();
	
	void addFocusChangeListener(FocusChangeListener listener);
	
	void removeFocusChangeListener(FocusChangeListener listener);
	
}
