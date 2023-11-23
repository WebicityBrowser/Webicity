package com.github.webicitybrowser.thready.dimensions;

/**
 * This interface is the base for 2D dimensions that use
 * only absolute units (pixels). It is meant to allow certain
 * utilities that work on absolute 2D dimensions to work on
 * multiple types of dimensions.
 */
public interface AbsoluteDimensions {
	
	float xComponent();

	float yComponent();

}
