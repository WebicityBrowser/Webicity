package everyos.desktop.thready.core.gui.directive.style;

import everyos.desktop.thready.core.gui.laf.ComponentUI;

public interface StyleGeneratorRoot {

	StyleGenerator generateChildStyleGenerator(ComponentUI componentUI);
	
	void onMassChange(Runnable listener);
	
}
