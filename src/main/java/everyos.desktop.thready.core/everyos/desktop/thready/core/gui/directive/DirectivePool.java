package everyos.desktop.thready.core.gui.directive;

import java.util.Optional;

public interface DirectivePool extends Iterable<Directive> {

	DirectivePool directive(Directive directive);
	
	Optional<Directive> getUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass);
	
	Optional<Directive> inheritUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass);
	
	Directive getUnresolvedDirective(Class<? extends Directive> directiveClass);
	
	Directive[] getCurrentDirectives();
	
	void addEventListener(DirectivePoolListener listener);
	
	void removeEventListener(DirectivePoolListener listener);
	
	@SuppressWarnings("unchecked")
	default <T extends Directive> Optional<T> getDirectiveOrEmpty(Class<T> directiveClass)  {
		return getUncastedDirectiveOrEmpty(directiveClass)
			.map(d -> (T) d);
	}
	
	@SuppressWarnings("unchecked")
	default <T extends Directive> Optional<T> inheritDirectiveOrEmpty(Class<T> directiveClass) {
		return inheritUncastedDirectiveOrEmpty(directiveClass)
			.map(d -> (T) d);
	}
	
}
