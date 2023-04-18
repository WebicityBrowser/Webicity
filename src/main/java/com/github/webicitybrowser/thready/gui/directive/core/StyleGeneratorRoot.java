package com.github.webicitybrowser.thready.gui.directive.core;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;

public interface StyleGeneratorRoot {

	StyleGenerator generateChildStyleGenerator(ComponentUI componentUI);
	
}
