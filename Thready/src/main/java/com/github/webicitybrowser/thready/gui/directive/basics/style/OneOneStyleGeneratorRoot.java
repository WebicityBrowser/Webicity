package com.github.webicitybrowser.thready.gui.directive.basics.style;

import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;

public class OneOneStyleGeneratorRoot implements StyleGeneratorRoot {

	@Override
	public StyleGenerator generateChildStyleGenerator(ComponentUI componentUI) {
		return new OneOneStyleGenerator(componentUI, null);
	}

}
