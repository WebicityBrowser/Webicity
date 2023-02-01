package everyos.desktop.thready.core.gui.directive;

import java.util.Optional;

public interface DirectivePool extends Iterable<Directive> {

	DirectivePool directive(Directive directive);
	
	<T extends Directive> T getDirective(Class<T> directiveClass);
	
	<T extends Directive> Optional<T> getDirectiveOrEmpty(Class<T> directiveClass);
	
	<T extends Directive> T getRawDirective(Class<T> directiveClass);
	
	Directive[] getCurrentDirectives();
	
}
