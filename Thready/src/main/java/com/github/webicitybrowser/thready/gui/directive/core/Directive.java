package com.github.webicitybrowser.thready.gui.directive.core;

/**
 * A directive allows styling of a component.
 */
public interface Directive {

	/**
	 * Get the type of this directive.
	 * @return The type of this directive.
	 */
	Class<? extends Directive> getPrimaryType();

}
