package com.github.webicitybrowser.thready.gui.directive.core.style;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;

public interface StyleGeneratorRoot {

	StyleGenerator generateChildStyleGenerator(ComponentUI componentUI, StyleContext styleContext);
	
}
