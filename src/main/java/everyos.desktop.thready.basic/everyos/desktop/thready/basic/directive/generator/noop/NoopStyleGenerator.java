package everyos.desktop.thready.basic.directive.generator.noop;

import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.laf.ComponentUI;

public class NoopStyleGenerator implements StyleGenerator {

	@Override
	public StyleGenerator[] createChildStyleGenerators(ComponentUI[] children) {
		StyleGenerator[] generators = new StyleGenerator[children.length];
		for (int i = 0; i < children.length; i++) {
			generators[i] = new NoopStyleGenerator();
		}
		
		return generators;
	}

	@Override
	public DirectivePool[] getDirectivePools() {
		return new DirectivePool[0];
	}

}
