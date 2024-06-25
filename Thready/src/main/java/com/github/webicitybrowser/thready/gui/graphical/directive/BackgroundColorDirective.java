package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;

/**
 * Allows setting the background color of a component
 * (if supported).
 */
public interface BackgroundColorDirective extends GraphicalDirective {
	
	/**
	 * Get the color the background of the component should be.
	 * @return The color the background of the component should be.
	 */
	ColorFormat getColor();
	
	@Override
	default Class<? extends Directive> getPrimaryType() {
		return BackgroundColorDirective.class;
	}
	
	@Override
	default InvalidationLevel getInvalidationLevel() {
		return InvalidationLevel.PAINT;
	};

	/**
	 * Create a new {@link BackgroundColorDirective} for the given color.
	 * @param color The color for a component's background.
	 * @return The relevant. directive.
	 */
	public static BackgroundColorDirective of(ColorFormat color) {
		return () -> color;
	}

}
