package everyos.desktop.thready.core.gui.directive.interlaced;

import everyos.desktop.thready.core.gui.directive.Directive;

public interface InterlacedDirective<T extends Directive> extends Directive {

	T getAt(long millisSinceStart);
	
	long getTimeUntilNextStep(long millisSinceStart);
	
}
