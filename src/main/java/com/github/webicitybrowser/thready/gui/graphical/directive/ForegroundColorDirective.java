package com.github.webicitybrowser.thready.gui.graphical.directive;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

/**
 * Allows setting the foreground color of a component
 * (if supported).
 */
public interface ForegroundColorDirective extends Directive {
	
	/**
	 * Get the color the background of the component should be.
	 * @return The color the background of the component should be.
	 */
	ColorFormat getColor();
	
	default Class<? extends Directive> getPrimaryType() {
		return ForegroundColorDirective.class;
	}

	/**
	 * Create a new {@link BackgroundColorDirective} for the given color.
	 * @param color The color for a component's background.
	 * @return The relevant. directive.
	 */
	public static ForegroundColorDirective of(ColorFormat color) {
		return () -> color;
	}

}
