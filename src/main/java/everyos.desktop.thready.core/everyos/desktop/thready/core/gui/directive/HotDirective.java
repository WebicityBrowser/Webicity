package everyos.desktop.thready.core.gui.directive;

public interface HotDirective extends Directive {

	void bind(DirectivePool pool);
	
	void unbind(DirectivePool pool);
	
	Directive getCurrentDirective();
	
}
