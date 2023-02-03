package everyos.desktop.thready.basic.directive.generator.noop;

import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.directive.style.StyleGeneratorRoot;
import everyos.desktop.thready.core.gui.laf.ComponentUI;

public class NoopStyleGeneratorRoot implements StyleGeneratorRoot {

	@Override
	public StyleGenerator generateChildStyleGenerator(ComponentUI componentUI) {
		return new NoopStyleGenerator();
	}

	@Override
	public void onMassChange(Runnable listener) {
		
	}

}
