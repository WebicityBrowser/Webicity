package com.github.webicitybrowser.thready.gui.directive.basics.style;

import com.github.webicitybrowser.thready.gui.directive.core.StyleGenerator;
import com.github.webicitybrowser.thready.gui.directive.core.StyleGeneratorRoot;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;

public class NoopStyleGeneratorRoot implements StyleGeneratorRoot {

	@Override
	public StyleGenerator generateChildStyleGenerator(ComponentUI componentUI) {
		return new NoopStyleGenerator();
	}

}
