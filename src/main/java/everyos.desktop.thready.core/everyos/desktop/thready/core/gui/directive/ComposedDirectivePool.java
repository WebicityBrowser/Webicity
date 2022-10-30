package everyos.desktop.thready.core.gui.directive;

public interface ComposedDirectivePool extends DirectivePool {

	void addDirectivePool(DirectivePool pool);
	
	void removeDirectivePool(DirectivePool pool);
	
	// Includes self
	DirectivePool[] getCurrentDirectivePools();
	
	// Note: A composed directive pool's own set of directives typically
	// take priority over those of any other pools added to it,
	// with few exceptions
	
}
