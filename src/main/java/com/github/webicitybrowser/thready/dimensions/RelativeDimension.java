package com.github.webicitybrowser.thready.dimensions;

public record RelativeDimension(float relativeComponent, float absoluteComponent) {

	public static float UNBOUNDED = -1;
	
	public float resolveAbsoluteDimensions(float parentDimension) {
		if (relativeComponent == RelativeDimension.UNBOUNDED) {
			return RelativeDimension.UNBOUNDED;
		}
		
		return relativeComponent * parentDimension + absoluteComponent;
	}
	
}
