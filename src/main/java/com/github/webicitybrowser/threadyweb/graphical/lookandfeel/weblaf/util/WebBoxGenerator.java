package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import java.util.function.Supplier;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective.OuterDisplay;

public final class WebBoxGenerator {

private WebBoxGenerator() {}
	
	public static Box[] generateBoxes(DirectivePool directives, Supplier<Box[]> defaultBoxGenerator) {
		OuterDisplay outerDisplay = WebDirectiveUtil.getOuterDisplay(directives);
		switch (outerDisplay) {
		case NONE:
			return new Box[0];
		default:
			return defaultBoxGenerator.get();
		}
	}
	
}
