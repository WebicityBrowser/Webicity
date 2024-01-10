package com.github.webicitybrowser.thready.gui.directive.core.pool;

import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

/**
 * A directive pool allows storing styling information in
 * the form of directives.
 */
public interface DirectivePool {

	/**
	 * Add a directive to this pool.
	 * @param directive The directive to be added.
	 * @return This pool.
	 */
	DirectivePool directive(Directive directive);
	
	/**
	 * Get a directive by it's type.
	 * @param directiveCls The type of directive to be gotten.
	 * @return An optional with the directive that has been gotten,
	 *  or empty if the directive has not been set.
	 */
	Optional<Directive> getUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass);
	
	/**
	 * Get a directive by it's type. Inherit if not set.
	 * @param directiveCls The type of directive to be gotten.
	 * @return An optional with the directive that has been gotten,
	 *  or empty if the directive has not been set or inherited.
	 */
	Optional<Directive> inheritUncastedDirectiveOrEmpty(Class<? extends Directive> directiveClass);
	
	/**
	 * Get a directive by it's type.
	 * @param <T> The interface of the directive to be gotten.
	 * @param directiveCls The type of directive to be gotten.
	 * @return An optional with the directive that has been gotten,
	 *  or empty if the directive has not been set.
	 */
	@SuppressWarnings("unchecked")
	default <T extends Directive> Optional<T> getDirectiveOrEmpty(Class<T> directiveClass)  {
		return getUncastedDirectiveOrEmpty(directiveClass)
			.map(d -> (T) d);
	}
	
	/**
	 * Get a directive by it's type. Inherit if not set.
	 * @param <T> The interface of the directive to be gotten.
	 * @param directiveCls The type of directive to be gotten.
	 * @return An optional with the directive that has been gotten,
	 *  or empty if the directive has not been set or inherited.
	 */
	@SuppressWarnings("unchecked")
	default <T extends Directive> Optional<T> inheritDirectiveOrEmpty(Class<T> directiveClass) {
		return inheritUncastedDirectiveOrEmpty(directiveClass)
			.map(d -> (T) d);
	}

	Directive getUnresolvedDirective(Class<? extends Directive> directiveClass);
	
	void addEventListener(DirectivePoolListener listener);
	
	void removeEventListener(DirectivePoolListener listener);
	
}
