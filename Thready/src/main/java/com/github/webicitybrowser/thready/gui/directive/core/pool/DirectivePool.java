package com.github.webicitybrowser.thready.gui.directive.core.pool;

import java.util.Optional;
import java.util.function.BiFunction;

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
	 * @param <T> The interface of the directive to be gotten.
	 * @param directiveCls The type of directive to be gotten.
	 * @return An optional with the directive that has been gotten,
	 *  or empty if the directive has not been set.
	 */
	<T extends Directive> Optional<T> getDirectiveOrEmpty(Class<T> directiveClass);
	
	/**
	 * Get a directive by it's type. Inherit if not set.
	 * @param <T> The interface of the directive to be gotten.
	 * @param directiveCls The type of directive to be gotten.
	 * @return An optional with the directive that has been gotten,
	 *  or empty if the directive has not been set or inherited.
	 */
	<T extends Directive> Optional<T> inheritDirectiveOrEmpty(Class<T> directiveClass);

	<T extends Directive> T derive(Class<T> directiveClass, BiFunction<DirectivePool, DirectivePool, T> deriveFunction);
	
	void addEventListener(DirectivePoolListener listener);
	
	void removeEventListener(DirectivePoolListener listener);
	
}
