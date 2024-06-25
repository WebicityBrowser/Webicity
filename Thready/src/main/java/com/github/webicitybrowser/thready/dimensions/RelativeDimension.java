package com.github.webicitybrowser.thready.dimensions;

/**
 * A relative dimension is computed in relation to an
 * absolute dimension.
 * @param relativeComponent A percentage of the parent dimension.
 * @param absoluteDimension A fixed portion added after the the relative
 *  portion is computed.
 */
public record RelativeDimension(float relativeComponent, float absoluteComponent) {

	/**
	 * Represents an unbounded dimension.
	 */
	// TODO: Make this positive infinity and fix resulting bugs
	public static float UNBOUNDED = Integer.MIN_VALUE;
	
	public float resolveAbsoluteDimensions(float parentDimension) {
		if (relativeComponent == RelativeDimension.UNBOUNDED) {
			return RelativeDimension.UNBOUNDED;
		}
		
		return relativeComponent * parentDimension + absoluteComponent;
	}
	
}
