package com.github.webicitybrowser.thready.dimensions;

public record RelativeSize(RelativeDimension width, RelativeDimension height) {

	public RelativeSize(float rw, float aw, float rh, float ah) {
		this(new RelativeDimension(rw, aw), new RelativeDimension(rh, ah));
	}

	public AbsoluteSize resolveAbsoluteSize(AbsoluteSize parentSize) {
		return new AbsoluteSize(
			width.resolveAbsoluteDimensions(parentSize.width()),
			height.resolveAbsoluteDimensions(parentSize.height()));
	}
	
}
