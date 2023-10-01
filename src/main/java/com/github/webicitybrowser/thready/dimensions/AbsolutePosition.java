package com.github.webicitybrowser.thready.dimensions;

/**
 * Represents a (x,y) position that is either not relative
 * to another object or is already resolved to absolute values.
 */
public record AbsolutePosition(float x, float y) implements AbsoluteDimensions {

	@Override
	public float xComponent() {
		return x;
	}

	@Override
	public float yComponent() {
		return y;
	}
	
}
