package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util;

import java.util.function.Supplier;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public final class SimpleBoxGenerator {

private SimpleBoxGenerator() {}
	
	public static Box[] generateBoxes(Supplier<Box[]> defaultBoxGenerator) {
		return defaultBoxGenerator.get();
	}
	
}
