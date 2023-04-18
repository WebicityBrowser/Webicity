package com.github.webicitybrowser.thready.gui.directive.basics.style;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;

public class NoopStyleGenerator implements StyleGenerator {

	@Override
	public StyleGenerator[] createChildStyleGenerators(ComponentUI[] children) {
		StyleGenerator[] childStyleGenerators = new StyleGenerator[children.length];
		for (int i = 0; i < children.length; i++) {
			childStyleGenerators[i] = this;
		}
		
		return childStyleGenerators;
	}

	@Override
	public DirectivePool[] getDirectivePools() {
		return new DirectivePool[0];
	}

}
