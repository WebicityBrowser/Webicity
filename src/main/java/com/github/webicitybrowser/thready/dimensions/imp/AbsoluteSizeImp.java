package com.github.webicitybrowser.thready.dimensions.imp;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public class AbsoluteSizeImp implements AbsoluteSize {

	private float width;
	private float height;

	public AbsoluteSizeImp(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public float getWidth() {
		return this.width;
	}

	@Override
	public float getHeight() {
		return this.height;
	}

}
