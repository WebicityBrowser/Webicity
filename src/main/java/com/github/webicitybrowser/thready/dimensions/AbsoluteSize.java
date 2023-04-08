package com.github.webicitybrowser.thready.dimensions;

/**
 * Represents a (w,h) size that is either not relative
 * to another object or is already resolved to absolute values.
 */
public record AbsoluteSize(float width, float height) {
	
}
