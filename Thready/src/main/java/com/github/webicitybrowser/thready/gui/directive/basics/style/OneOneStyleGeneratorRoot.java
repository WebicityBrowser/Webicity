package com.github.webicitybrowser.thready.gui.directive.basics.style;

import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;

public class OneOneStyleGeneratorRoot implements StyleGeneratorRoot {

	@Override
	public StyleGenerator generateChildStyleGenerator(ComponentUI componentUI, StyleContext styleContext) {
		return new OneOneStyleGenerator(componentUI, null);
	}

}
