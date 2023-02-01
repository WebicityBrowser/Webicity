package everyos.desktop.thready.laf.simple.util;

import java.util.function.Supplier;

import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.stage.box.Box;

public final class SimpleBoxGenerator {

	private SimpleBoxGenerator() {}
	
	public static Box[] generateBoxes(DirectivePool directives, Supplier<Box[]> defaultBoxGenerator) {
		return defaultBoxGenerator.get();
	}
	
}
