package everyos.desktop.thready.core.gui.directive.interlaced;

import everyos.desktop.thready.core.gui.directive.Directive;

public interface InterlaceInfo<T extends Directive> {

	int getDuration();
	
	T getDirective();
	
}
