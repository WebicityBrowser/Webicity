package com.github.webicitybrowser.thready.dimensions;

public record RelativePosition(RelativeDimension x, RelativeDimension y) {

	public RelativePosition(float rx, float ax, float ry, float ay) {
		this(new RelativeDimension(rx, ax), new RelativeDimension(ry, ay));
	}
	
	public AbsolutePosition resolveAbsolutePosition(AbsoluteSize parentSize) {
		return new AbsolutePosition(
			x.resolveAbsoluteDimensions(parentSize.width()),
			y.resolveAbsoluteDimensions(parentSize.height()));
	}
	
}
