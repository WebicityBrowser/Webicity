package everyos.desktop.thready.core.gui.directive.interlaced;

import everyos.desktop.thready.core.gui.directive.Directive;

public interface DirectiveInterlacer<T extends Directive> {

	InterlacedDirective<T> interlace(InterlaceInfo<T>[] directives, InterlaceOptions options);
	
}
