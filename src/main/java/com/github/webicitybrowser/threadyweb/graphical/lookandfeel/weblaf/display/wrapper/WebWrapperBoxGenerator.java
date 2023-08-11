package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import java.util.List;
import java.util.function.Supplier;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective.OuterDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;

public final class WebWrapperBoxGenerator {

	private WebWrapperBoxGenerator() {}
	
	public static <U extends Box> List<U> generateBoxes(DirectivePool directives, Supplier<List<U>> defaultBoxGenerator) {
		OuterDisplay outerDisplay = WebDirectiveUtil.getOuterDisplay(directives);
		switch (outerDisplay) {
		case NONE:
			return List.of();
		default:
			return defaultBoxGenerator.get();
		}
	}
	
}
