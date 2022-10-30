package everyos.desktop.thready.core.gui.directive;

public interface InterlacedDirective extends Directive {

	Directive getAt(long millisSinceStart);
	
	long getTimeUntilNextStep(long millisSinceStart);
	
}
