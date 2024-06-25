package com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor;

import com.github.webicitybrowser.thready.dimensions.AbsoluteDimensions;

public record LineDimension(float run, float depth, LineDirection direction) implements AbsoluteDimensions {

	@Override
	public float xComponent() {
		return run;
	}

	@Override
	public float yComponent() {
		return depth;
	}

	public static enum LineDirection {
		LTR, RTL, TTB, BTT
	}

}
