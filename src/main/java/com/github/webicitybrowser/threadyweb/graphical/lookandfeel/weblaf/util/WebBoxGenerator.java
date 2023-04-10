package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import java.util.function.Supplier;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public final class WebBoxGenerator {

private WebBoxGenerator() {}
	
	public static Box[] generateBoxes(Supplier<Box[]> defaultBoxGenerator) {
		return defaultBoxGenerator.get();
	}
	
}
