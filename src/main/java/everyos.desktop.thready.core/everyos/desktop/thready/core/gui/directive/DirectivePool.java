package everyos.desktop.thready.core.gui.directive;

public interface DirectivePool extends Iterable<Directive> {

	DirectivePool directive(Directive directive);
	
	<T extends Directive> T getDirective(Class<T> directiveClass);
	
	<T extends Directive> T getDirectiveOrDefault(Class<T> directiveClass, T def);
	
	<T extends Directive> T getRawDirective(Class<T> directiveClass);
	
	Directive[] getCurrentDirectives();
	
}
