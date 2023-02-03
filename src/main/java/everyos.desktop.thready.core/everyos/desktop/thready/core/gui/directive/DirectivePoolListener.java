package everyos.desktop.thready.core.gui.directive;

public interface DirectivePoolListener {

	void onDirective(Class<? extends Directive> directiveCls);
	
	void onMassChange();
	
}
