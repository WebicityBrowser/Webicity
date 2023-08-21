package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;

public interface Context {
	
	UIDisplay<?, ?, ?> display();

	ComponentUI componentUI();

}
